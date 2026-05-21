package com.dbd.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

public class RpgSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:3000";

    private static boolean isServerRunning(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1500);
            conn.setReadTimeout(1500);
            conn.connect();
            int respCode = conn.getResponseCode();
            return respCode == 200 || respCode == 302;
        } catch (Exception e) {
            return false;
        }
    }

    @BeforeEach
    public void setUp() {
        // Validar si el frontend de Next.js está en ejecución
        boolean running = isServerRunning(BASE_URL + "/login");
        Assumptions.assumeTrue(running, "Next.js dev server no está en ejecución en " + BASE_URL + ", omitiendo prueba de Selenium.");

        driver = createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private WebDriver createDriver() {
        try {
            // Intentamos Edge en modo silencioso (headless)
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            return new EdgeDriver(options);
        } catch (Exception e) {
            System.out.println("EdgeDriver no disponible o falló, intentando con ChromeDriver...");
            try {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(options);
            } catch (Exception ex) {
                System.err.println("Error crítico: No se encontró ningún navegador Edge o Chrome compatible en el sistema: " + ex.getMessage());
                throw new RuntimeException("Selenium requiere Edge o Chrome instalado en el sistema para ejecutar las pruebas.", ex);
            }
        }
    }

    @Test
    public void testFlujoCompletoCincoUsuarios() throws Exception {
        System.out.println("=== INICIANDO SUITE DE PRUEBAS DE INTERFAZ CON SELENIUM (5 USUARIOS) ===");

        for (int i = 1; i <= 5; i++) {
            String uniqueUser = "user_test_" + i + "_" + UUID.randomUUID().toString().substring(0, 5);
            String password = "password123";
            System.out.println("\n--- Automatizando Usuario " + i + ": " + uniqueUser + " ---");

            // 1. Ir al login
            driver.get(BASE_URL + "/login");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Sobreviviente o Asesino']")));
            
            // Esperar a que la hidratación de Next.js se complete y los eventos estén registrados
            Thread.sleep(3000);

            // 2. Rellenar inputs
            WebElement userField = driver.findElement(By.xpath("//input[@placeholder='Sobreviviente o Asesino']"));
            WebElement passField = driver.findElement(By.xpath("//input[@placeholder='Contraseña Secreta']"));

            userField.sendKeys(uniqueUser);
            passField.sendKeys(password);

            // 3. Click en 'Firmar Registro' para registrarse
            WebElement registerBtn = driver.findElement(By.xpath("//button[contains(text(), 'Firmar Registro')]"));
            registerBtn.click();

            // 4. Esperar redirección al Dashboard
            System.out.println("Esperando redirección al Dashboard...");
            try {
                wait.until(ExpectedConditions.urlContains("/dashboard"));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'MENU PRINCIPAL')]")));
                System.out.println("¡Autenticado y dentro del Menu Principal!");
            } catch (Exception te) {
                System.err.println("[DEBUG ERROR] Falló la redirección al Dashboard.");
                System.err.println("URL Actual: " + driver.getCurrentUrl());
                System.err.println("Cookies actuales: " + driver.manage().getCookies());
                try {
                    WebElement errorElem = driver.findElement(By.xpath("//*[contains(@class, 'bg-zinc-950/80') or contains(@class, 'animate-pulse')]"));
                    System.err.println("Mensaje de error visible en pantalla: " + errorElem.getText());
                } catch (Exception ex) {
                    System.err.println("No se encontró ningún elemento de error visible.");
                }
                System.err.println("Código Fuente HTML de la página:");
                System.err.println(driver.getPageSource());
                throw te;
            }

            // 5. Iniciar una nueva partida (click en tarjeta Nueva Partida)
            WebElement newGameCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//h2[contains(text(), 'Nueva Partida')]")
            ));
            newGameCard.click();

            // 6. Esperar a que abra el modal de ranuras
            System.out.println("Seleccionando Ranura 1 para nueva partida...");
            WebElement selectSlotBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[contains(@class, 'fixed')]//button[contains(text(), 'Seleccionar')])[1]")
            ));
            selectSlotBtn.click();

            // Si la ranura tiene una partida guardada previa, Next.js lanza un alert de confirmación de JS
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                System.out.println("Alerta detectada: " + alert.getText() + ". Aceptando...");
                alert.accept();
            } catch (Exception e) {
                // No había partida previa en la ranura, omitimos la confirmación sin fallo
            }

            // 7. Esperar redirección a 'La Hoguera' (/play)
            System.out.println("Esperando redirección a La Hoguera...");
            wait.until(ExpectedConditions.urlContains("/play"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'LA HOGUERA')]")));

            // 8. Click en Azar para seleccionar supervivientes y asesinos aleatoriamente
            WebElement azarBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Azar') or contains(., '🎲')]")
            ));
            azarBtn.click();
            Thread.sleep(1000); // Pequeño delay de transición visual

            // 9. Click en 'Jugar Modo Automatico'
            System.out.println("Iniciando prueba en Modo Automático...");
            WebElement autoModeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Jugar Modo Automatico') or contains(., 'Automatico')]")
            ));
            autoModeBtn.click();

            // 10. Esperar redirección a la Prueba (/trial)
            System.out.println("Esperando redirección a La Prueba (/trial)...");
            wait.until(ExpectedConditions.urlContains("/trial"));
            Thread.sleep(1500); // Permitir carga de animaciones del juego

            // 11. Click en 'GUARDAR' para sellar el progreso en base de datos
            System.out.println("Guardando el estado de la partida...");
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'GUARDAR') or contains(., 'Guardar')]")
            ));
            saveBtn.click();

            // 12. Modal de guardado: click en 'Sellar Aquí' en Ranura 1
            WebElement sellarBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[contains(@class, 'fixed')]//button[contains(text(), 'Sellar') or contains(text(), 'Aquí') or contains(., 'Sellar')])[1]")
            ));
            sellarBtn.click();

            // 13. Esperar que se guarde la partida y regrese al Dashboard
            System.out.println("Esperando regreso al Dashboard...");
            wait.until(ExpectedConditions.urlContains("/dashboard"));

            // 14. Cerrar sesión ('Escapar') de forma segura
            System.out.println("Cerrando sesión de " + uniqueUser + "...");
            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Escapar') or contains(., 'Escapar')]")
            ));
            logoutBtn.click();

            // 15. Esperar regreso a Login
            wait.until(ExpectedConditions.urlContains("/login"));
            System.out.println("Sesión cerrada con éxito para " + uniqueUser + ". Flujo completado.");
        }

        System.out.println("\n=== TODAS LAS 5 PRUEBAS DE SELENIUM COMPLETADAS CON ÉXITO ===");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
