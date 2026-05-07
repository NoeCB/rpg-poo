import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartidaWebTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testFlujoDeCombate() {
        // 1. Entrar a la web
        driver.get("http://localhost:8080");

        // 2. Seleccionar 3 Supervivientes
        wait.until(ExpectedConditions.elementToBeClickable(By.id("img-LeonKennedy"))).click();
        driver.findElement(By.id("img-FengMin")).click();
        driver.findElement(By.id("img-AdaWong")).click();

        // 3. Seleccionar 3 Asesinos
        driver.findElement(By.id("img-GhostFace")).click();
        driver.findElement(By.id("img-Legion")).click();
        driver.findElement(By.id("img-Wesker")).click();

        // 4. Iniciar la Partida (Trial)
        driver.findElement(By.id("btn-start-trial")).click();

        // 5. Esperar a que cargue la pantalla de combate y verificar que el log de
        // texto aparece
        WebElement combatLog = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("combat-log")));
        assertTrue(combatLog.isDisplayed(), "El registro de combate debería ser visible");

        // 6. Hacer clic en el botón de ATACAR (Suponiendo que tienes un botón con id
        // 'btn-atacar' en tu HTML)
        // Nota: Ajusta este ID al que tengas realmente en tu código de combate
        WebElement btnAtacar = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-atacar")));
        btnAtacar.click();

        // 7. Seleccionar al primer objetivo de la lista generada dinámicamente
        // Como tu JS crea botones dentro de 'selection-list', buscamos el primer botón
        // ahí
        WebElement primerObjetivo = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#selection-list button")));
        primerObjetivo.click();

        // 8. Verificar que el ataque ha generado un nuevo mensaje en el log
        assertTrue(combatLog.getText().contains("daño") || combatLog.getText().contains("atacó"),
                "El log debería registrar el ataque");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
