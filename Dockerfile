# Используем официальный образ Playwright для Java
FROM mcr.microsoft.com/playwright/java:v1.57.0-noble

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml для кеширования зависимостей
COPY pom.xml .

# Загружаем зависимости Maven (кешируется в отдельном слое)
RUN mvn dependency:go-offline -B

# Копируем исходный код
COPY src ./src

# Устанавливаем переменные окружения
ENV HEADLESS=true
ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright
ENV BASE_URL=https://otus.ru
ENV WAITER_TIMEOUT=10

# Создаем директорию для результатов тестов
RUN mkdir -p /app/target/surefire-reports /app/target/traces

# Запускаем тесты
# Используем -Dheadless=true для запуска браузеров в headless режиме
# Можно переопределить через docker run с аргументами: mvn clean test -Dheadless=true
CMD ["mvn", "clean", "test", "-Dheadless=true"]
