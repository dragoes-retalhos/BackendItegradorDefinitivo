# Usar uma imagem base do Maven para compilar a aplicação e para desenvolvimento
FROM maven:3.8.3-openjdk-17 AS build

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml e baixar dependências para cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar o código-fonte da aplicação
COPY src ./src

# Adicionar suporte a variáveis de ambiente
ENV UPLOAD_DIR=/app/uploads

# Comando para compilar e executar a aplicação em modo de desenvolvimento
CMD ["mvn", "spring-boot:run"]
