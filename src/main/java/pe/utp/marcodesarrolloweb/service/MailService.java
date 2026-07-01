package pe.utp.marcodesarrolloweb.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class MailService {

  private static final Logger log = LoggerFactory.getLogger(MailService.class);
  private static final String FROM = "no-reply@instituto-horizonte.edu.pe";
  private static final DateTimeFormatter DATE_FMT =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  private final JavaMailSender mailSender;

  public MailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendPaymentLink(String toEmail, String studentName, String programName,
      String periodName, BigDecimal amount, String currency, String approveUrl) {
    String amountStr = currency + " " + String.format("%.2f", amount);
    String html = header("#1e293b", "Instituto Horizonte", "Sistema de Matrículas", "#94a3b8")
        + "<div style='padding:32px'>"
        + "<p style='color:#374151;font-size:16px;margin:0 0 8px'>Hola, <strong>" + studentName + "</strong></p>"
        + "<p style='color:#6b7280;font-size:14px;margin:0 0 24px'>"
        + "Tu matrícula ha sido registrada exitosamente. Para activarla, completa el pago a través de PayPal.</p>"
        + "<div style='background:#f8fafc;border:1px solid #e2e8f0;border-radius:8px;padding:20px;margin-bottom:28px'>"
        + "<table style='width:100%;border-collapse:collapse;font-size:14px'>"
        + row("📚 Programa", programName, "", "")
        + row("📅 Periodo", periodName, "border-top:1px solid #e5e7eb", "border-top:1px solid #e5e7eb")
        + row("💰 Monto", "<strong style='font-size:16px'>" + amountStr + "</strong>",
              "border-top:1px solid #e5e7eb", "border-top:1px solid #e5e7eb")
        + "</table></div>"
        + "<div style='text-align:center;margin-bottom:24px'>"
        + "<a href='" + approveUrl + "' style='display:inline-block;background:#0070ba;color:#fff;"
        + "padding:14px 36px;border-radius:6px;text-decoration:none;font-weight:700;font-size:15px'>"
        + "Pagar con PayPal</a></div>"
        + "<p style='font-size:12px;color:#9ca3af;text-align:center;margin:0'>"
        + "Si el botón no funciona, copia este enlace en tu navegador:<br>"
        + "<a href='" + approveUrl + "' style='color:#0070ba;word-break:break-all'>" + approveUrl + "</a></p>"
        + "</div>"
        + footer();
    send(toEmail, "Completa el pago de tu matrícula – " + programName, html);
  }

  public void sendPaymentConfirmation(String toEmail, String studentName, String programName,
      String periodName, BigDecimal amount, String currency, LocalDateTime paidAt) {
    String amountStr = currency + " " + String.format("%.2f", amount);
    String paidAtStr = paidAt != null ? paidAt.format(DATE_FMT) : "—";
    String html = header("#15803d", "✅ ¡Matrícula confirmada!", "Tu pago fue procesado correctamente", "#bbf7d0")
        + "<div style='padding:32px'>"
        + "<p style='color:#374151;font-size:16px;margin:0 0 8px'>¡Felicidades, <strong>" + studentName + "</strong>!</p>"
        + "<p style='color:#6b7280;font-size:14px;margin:0 0 24px'>"
        + "Tu matrícula ha sido activada. A continuación el resumen de tu pago:</p>"
        + "<div style='background:#f0fdf4;border:1px solid #bbf7d0;border-radius:8px;padding:20px;margin-bottom:28px'>"
        + "<table style='width:100%;border-collapse:collapse;font-size:14px'>"
        + row("📚 Programa", programName, "", "")
        + row("📅 Periodo", periodName, "border-top:1px solid #dcfce7", "border-top:1px solid #dcfce7")
        + row("💰 Monto pagado", "<strong style='color:#15803d;font-size:16px'>" + amountStr + "</strong>",
              "border-top:1px solid #dcfce7", "border-top:1px solid #dcfce7")
        + row("📆 Fecha de pago", paidAtStr, "border-top:1px solid #dcfce7", "border-top:1px solid #dcfce7")
        + "</table></div>"
        + "<p style='color:#6b7280;font-size:13px;text-align:center;margin:0'>"
        + "Guarda este correo como comprobante de tu matrícula.</p>"
        + "</div>"
        + footer();
    send(toEmail, "¡Matrícula confirmada! – " + programName, html);
  }

  public void sendCancellationEmail(String toEmail, String studentName, String programName,
      String periodName) {
    String html = header("#b45309", "❌ Matrícula cancelada", "Te informamos sobre el estado de tu matrícula", "#fde68a")
        + "<div style='padding:32px'>"
        + "<p style='color:#374151;font-size:16px;margin:0 0 8px'>Hola, <strong>" + studentName + "</strong></p>"
        + "<p style='color:#6b7280;font-size:14px;margin:0 0 24px'>"
        + "Lamentamos informarte que tu matrícula ha sido cancelada. A continuación el detalle:</p>"
        + "<div style='background:#fffbeb;border:1px solid #fde68a;border-radius:8px;padding:20px;margin-bottom:28px'>"
        + "<table style='width:100%;border-collapse:collapse;font-size:14px'>"
        + row("📚 Programa", programName, "", "")
        + row("📅 Periodo", periodName, "border-top:1px solid #fde68a", "border-top:1px solid #fde68a")
        + "</table></div>"
        + "<p style='color:#6b7280;font-size:14px;margin:0 0 8px'>"
        + "Si crees que esto es un error o deseas reinscribirte, comunícate con nosotros:</p>"
        + "<p style='color:#374151;font-size:14px;font-weight:600;margin:0'>"
        + "📧 secretaria@instituto-horizonte.edu.pe</p>"
        + "</div>"
        + footer();
    send(toEmail, "Tu matrícula ha sido cancelada – " + programName, html);
  }

  // ── helpers ───────────────────────────────────────────────────────────────

  private String header(String bg, String title, String subtitle, String subtitleColor) {
    return "<div style='font-family:\"Segoe UI\",Arial,sans-serif;max-width:600px;margin:0 auto;"
        + "border:1px solid #e5e7eb;border-radius:8px;overflow:hidden'>"
        + "<div style='background:" + bg + ";padding:24px 32px;text-align:center'>"
        + "<h1 style='color:#fff;margin:0;font-size:22px'>" + title + "</h1>"
        + "<p style='color:" + subtitleColor + ";margin:6px 0 0;font-size:14px'>" + subtitle + "</p>"
        + "</div>";
  }

  private String row(String label, String value, String labelStyle, String valueStyle) {
    return "<tr>"
        + "<td style='padding:8px 0;color:#6b7280;width:40%;" + labelStyle + "'>" + label + "</td>"
        + "<td style='padding:8px 0;color:#111827;font-weight:600;" + valueStyle + "'>" + value + "</td>"
        + "</tr>";
  }

  private String footer() {
    return "<div style='background:#f1f5f9;padding:16px 32px;text-align:center;border-top:1px solid #e2e8f0'>"
        + "<p style='font-size:12px;color:#94a3b8;margin:0'>"
        + "Este correo fue enviado automáticamente. Por favor no respondas a este mensaje.<br>"
        + "© Instituto Horizonte</p>"
        + "</div></div>";
  }

  private void send(String toEmail, String subject, String html) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
      helper.setTo(toEmail);
      helper.setFrom(FROM);
      helper.setSubject(subject);
      helper.setText(html, true);
      mailSender.send(message);
      log.info("Correo enviado a {} — {}", toEmail, subject);
    } catch (MessagingException e) {
      log.error("No se pudo enviar el correo a {} — {}: {}", toEmail, subject, e.getMessage());
    }
  }
}
