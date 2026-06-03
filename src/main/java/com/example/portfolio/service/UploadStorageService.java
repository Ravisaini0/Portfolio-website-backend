package com.example.portfolio.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadStorageService {

    private static final int MAX_IMAGE_WIDTH = 1400;
    private static final int MAX_IMAGE_HEIGHT = 900;
    private static final float JPEG_QUALITY = 0.82f;

    private final Path uploadDir = Path.of("uploads").toAbsolutePath().normalize();

    public String store(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        Files.createDirectories(uploadDir);

        String contentType = file.getContentType() == null ? "" : file.getContentType().toLowerCase(Locale.ROOT);
        boolean optimizableImage = contentType.startsWith("image/")
                && !contentType.contains("svg")
                && !contentType.contains("gif");

        if (optimizableImage) {
            String storedName = System.currentTimeMillis() + "-" + safeBaseName(file) + ".jpg";
            Path target = safeTarget(storedName);

            try (InputStream input = file.getInputStream()) {
                BufferedImage source = ImageIO.read(input);
                if (source != null) {
                    writeOptimizedJpeg(source, target);
                    return "/uploads/" + storedName;
                }
            }
        }

        String storedName = System.currentTimeMillis() + "-" + safeOriginalName(file);
        Path target = safeTarget(storedName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + storedName;
    }

    public void deleteIfLocalUpload(String value) {
        if (value == null || value.isBlank()) {
            return;
        }

        String normalized = value.replace("\\", "/");
        int uploadIndex = normalized.indexOf("/uploads/");
        if (uploadIndex < 0) {
            return;
        }

        String fileName = normalized.substring(uploadIndex + "/uploads/".length());
        if (fileName.isBlank() || fileName.contains("/") || fileName.contains("..")) {
            return;
        }

        try {
            Path target = safeTarget(fileName);
            Files.deleteIfExists(target);
        } catch (IOException ignored) {
        }
    }

    private void writeOptimizedJpeg(BufferedImage source, Path target) throws IOException {
        int width = source.getWidth();
        int height = source.getHeight();
        double scale = Math.min(1.0, Math.min((double) MAX_IMAGE_WIDTH / width, (double) MAX_IMAGE_HEIGHT / height));
        int targetWidth = Math.max(1, (int) Math.round(width * scale));
        int targetHeight = Math.max(1, (int) Math.round(height * scale));

        BufferedImage output = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = output.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillRect(0, 0, targetWidth, targetHeight);
        graphics.drawImage(source, 0, 0, targetWidth, targetHeight, null);
        graphics.dispose();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            ImageIO.write(output, "jpg", target.toFile());
            return;
        }

        ImageWriter writer = writers.next();
        ImageWriteParam params = writer.getDefaultWriteParam();
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(JPEG_QUALITY);

        try (ImageOutputStream stream = ImageIO.createImageOutputStream(target.toFile())) {
            writer.setOutput(stream);
            writer.write(null, new IIOImage(output, null, null), params);
        } finally {
            writer.dispose();
        }
    }

    private Path safeTarget(String fileName) throws IOException {
        Path target = uploadDir.resolve(fileName).normalize();
        if (!target.startsWith(uploadDir)) {
            throw new IOException("Invalid upload path");
        }
        return target;
    }

    private String safeOriginalName(MultipartFile file) {
        String originalName = file.getOriginalFilename() == null ? "upload" : file.getOriginalFilename();
        return originalName.replaceAll("[^a-zA-Z0-9._-]", "-");
    }

    private String safeBaseName(MultipartFile file) {
        String safeName = safeOriginalName(file);
        return safeName.replaceFirst("\\.[^.]+$", "");
    }
}
