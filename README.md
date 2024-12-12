# Proyecto: Pipeline de Pruebas para Spring PetClinic

## Propósito

Este proyecto tiene como objetivo implementar un pipeline de integración continua para ejecutar pruebas automatizadas sobre la aplicación **Spring PetClinic**. El pipeline está diseñado para evaluar la calidad del software a través de varias etapas, asegurando que el sistema sea robusto, seguro y eficiente.

En cuanto a la aplicación a probar, **Spring PetClinic**, esta es una app de muestra creada con **Spring Boot** que sirve como un sistema de gestión para una clínica veterinaria. Incluye funcionalidades para registrar propietarios de mascotas, sus animales y visitas médicas.

## Instalación y Configuración del Pipeline CI/CD

El pipeline está diseñado para ejecutarse en Jenkins y automatizar las etapas clave del desarrollo del software. A continuación, se detallan los pasos para su instalación y configuración:

### Configuración de Jenkins

1. **Instalación de Plugins**:
   Asegúrate de que Jenkins tenga los siguientes plugins instalados:
   - Pipeline: Permite definir y ejecutar pipelines.
   - Git: Para clonar el repositorio del proyecto.
   - Maven Integration: Para la construcción del proyecto utilizando Maven.
   - Docker Pipeline: Para construir e implementar contenedores Docker.
   - SonarQube Scanner: Necesario para la integración con SonarQube.
   - JUnit Plugin: Para visualizar reportes de pruebas unitarias.
   - HTML Publisher: Para publicar reportes generados como HTML (e.g., Selenium o ZAP).
   - Performance Plugin: Para analizar y visualizar resultados de pruebas de rendimiento.

2. **Creación del Pipeline**:
   - Crera un nuevo job de tipo "Pipeline" en Jenkins.
   - En la sección "Pipeline Script":

     ```groovy
     pipeline {
         agent any
         stages {
             stage('Build') {
                 steps {
                     sh 'mvn clean install'
                 }
             }
             stage('Code Analysis') {
                 steps {
                     withSonarQubeEnv('SonarQube') {
                         sh 'mvn sonar:sonar'
                     }
                 }
             }
             stage('Unit Tests') {
                 steps {
                     sh 'mvn test'
                     junit '**/target/surefire-reports/*.xml'
                 }
             }
             stage('Functional Tests') {
                 steps {
                     sh 'java -jar selenium-tests.jar'
                 }
             }
             stage('Performance Tests') {
                 steps {
                     sh 'jmeter -n -t test.jmx -l results.jtl'
                 }
             }
             stage('Security Tests') {
                 steps {
                     sh 'zap-baseline.py -t http://localhost:8080 -r report.html'
                 }
             }
             stage('Deployment') {
                 steps {
                     sh 'docker build -t my-app .'
                     sh 'docker run -d -p 8080:8080 my-app'
                 }
             }
         }
     }
     ```

## Tecnologías Utilizadas

- **Spring Boot**: Framework base de la aplicación PetClinic.
- **Maven**: Herramienta de construcción.
- **SonarQube**: Análisis estático de código.
- **JUnit**: Marco para pruebas unitarias.
- **Selenium**: Automatización de pruebas funcionales.
- **JMeter**: Pruebas de rendimiento.
- **OWASP ZAP**: Escáner de seguridad.
- **Docker**: Contenerización de la aplicación.

## Ejecución del Pipeline

El pipeline está definido en un archivo de configuración que incluye todos los pasos mencionados. Para ejecutarlo, se puede utilizar Jenkins u otra herramienta compatible con pipelines de CI/CD.

### Comandos Clave

- **Análisis del código**:
  Realiza un análisis estático del código fuente utilizando SonarQube.
  ```bash
  mvn sonar:sonar
  ```
- **Pruebas unitarias**:
  Ejecuta pruebas unitarias para verificar la funcionalidad de las clases y métodos.
  ```bash
  mvn test
  ```
- **Pruebas funcionales**:
  Ejecuta pruebas funcionales que validan el comportamiento completo del sistema.
  ```bash
  java -jar selenium-tests.jar
  ```
- **Pruebas de rendimiento**:
  Evalúa el desempeño del sistema bajo diferentes condiciones de carga.
  ```bash
  jmeter -n -t test.jmx -l results.jtl
  ```
- **Pruebas de seguridad**:
  Identifica vulnerabilidades potenciales mediante OWASP ZAP.
  ```bash
  zap-baseline.py -t http://localhost:8080 -r report.html
  ```
- **Despliegue**:
  Construye una imagen Docker e implementa la aplicación en un contenedor.
  ```bash
  docker build -t my-app .
  docker run -d -p 8080:8080 my-app
  ```bash
  mvn clean install
  ```

## Resultados del Análisis del Código

El análisis de código realizado con SonarQube arrojó los siguientes resultados:

- **Líneas de código**: 792
- **Cobertura de pruebas**: 93.1% (272 líneas cubiertas).
- **Duplicación de código**: 1.9% (sobre 1.7k líneas).
- **Problemas de mantenibilidad**: 5 abiertos.
- **Problemas de seguridad**: 0 abiertos.
- **Fiabilidad**: 0 problemas abiertos.

## Resultados de las Pruebas Unitarias

Las pruebas unitarias realizadas con JUnit mostraron los siguientes resultados:

- **Total de pruebas**: 69
- **Errores**: 0
- **Fallos**: 0
- **Pruebas omitidas**: 0
- **Tasa de éxito**: 100%
- **Tiempo total**: 16.20 segundos

### Cobertura de Pruebas Unitarias (JaCoCo)

| Paquete                                         | Cobertura Instrucciones | Cobertura Ramas |
|------------------------------------------------|--------------------------|-----------------|
| org.springframework.samples.petclinic.owner   | 94%                      | 88%             |
| org.springframework.samples.petclinic         | 7%                       | N/A             |
| org.springframework.samples.petclinic.system  | 53%                      | N/A             |
| org.springframework.samples.petclinic.vet     | 100%                     | 100%            |
| org.springframework.samples.petclinic.model   | 100%                     | 100%            |
| **Total**                                      | **91%**                  | **89%**         |

## Resultados de las Pruebas Funcionales

Las pruebas funcionales realizadas con Selenium arrojaron los siguientes resultados:

- **Total de pruebas**: 6  
- **Errores**: 0  
- **Fallos**: 0  
- **Pruebas omitidas**: 0  
- **Tasa de éxito**: 100%  
- **Tiempo total**: 16.32 segundos  

### Detalle por Pruebas

| Nombre de Prueba                     | Errores | Fallos | Tiempo (s) |
|--------------------------------------|---------|--------|------------|
| Buscar Propietario                   | 0       | 0      | 1.041      |
| Editar Propietario                   | 0       | 0      | 3.346      |
| Agregar Visita                       | 0       | 0      | 2.915      |
| Agregar Propietario                  | 0       | 0      | 2.523      |
| Agregar Mascota                      | 0       | 0      | 4.000      |
| Editar Mascota                       | 0       | 0      | 2.440      |

## Resultados de las Pruebas de Rendimiento

Las pruebas de rendimiento realizadas con JMeter arrojaron los siguientes resultados:

- **Total de muestras**: 75,000  
- **Errores**: 0 (0.00%)  
- **Tiempo promedio de respuesta**: 4807.56 ms  
- **Tiempo máximo de respuesta**: 23,491 ms  
- **Tiempo mínimo de respuesta**: 1 ms  

### Detalle por Pruebas

| Etiqueta de Prueba        | Tiempo Promedio (ms) | Tiempo Mínimo (ms) | Tiempo Máximo (ms) | Errores (%) |
|---------------------------|----------------------|---------------------|---------------------|-------------|
| CSS                       | 2602.69             | 1                   | 4934               | 0.00%       |
| Editar Propietario        | 4231.04             | 63                  | 11,398             | 0.00%       |
| Buscar Propietario        | 2564.57             | 1                   | 4936               | 0.00%       |
| Buscar con Apellido Vacío| 5555.51             | 33                  | 15,940             | 0.00%       |
| Página Principal         | 2654.20             | 1                   | 4890               | 0.00%       |
| JavaScript                | 2564.66             | 1                   | 4937               | 0.00%       |
| Nuevo Propietario         | 8181.66             | 632                 | 21,750             | 0.00%       |
| Nueva Visita              | 4998.00             | 642                 | 12,887             | 0.00%       |
| POST Editar Propietario   | 4191.53             | 34                  | 11,942             | 0.00%       |
| POST Nuevo Propietario    | 8608.84             | 217                 | 23,346             | 0.00%       |
| POST Nueva Visita         | 8912.60             | 200                 | 23,491             | 0.00%       |

## Resultados de las Pruebas de Seguridad

Las pruebas de seguridad realizadas con OWASP ZAP arrjaron los siguientes hallazgos:

- **Alertas de nivel alto**: 0  
- **Alertas de nivel medio**: 4  
- **Alertas de nivel bajo**: 4  
- **Alertas informativas**: 3  

### Detalle de Alertas

| Nivel de Riesgo   | Descripción                                              | Instancias |
|-------------------|----------------------------------------------------------|------------|
| Medio            | Ausencia de Tokens Anti-CSRF                             | 1          |
| Medio            | Cabecera Content Security Policy (CSP) no configurada    | 8          |
| Medio            | Falta de cabecera Anti-Clickjacking                      | 8          |
| Medio            | Fuga de información de Spring Actuator                   | 1          |
| Bajo             | Divulgación de Información - Mensajes de Error de Depuración | 1          |
| Bajo             | Divulgación de error de aplicación                      | 1          |
| Bajo             | Falta encabezado X-Content-Type-Options                  | 14         |
| Bajo             | Inclusión de archivos fuente JavaScript entre dominios   | 16         |
| Informativo      | Atributo de elemento HTML controlable por el usuario (XSS potencial) | 7 |
| Informativo      | Divulgación de información - Comentarios sospechosos     | 1          |
| Informativo      | Librería JS Vulnerable                                   | 1          |


## Autores

- Grupo 5, Curso: Verificación y Validación de Software:
  
	- Cjuro Apaza, Jimmy Cristhian	
	- Espinoza Carlos, Diego Sebastian	
	- Espinoza Fabion, Josue Marcelo	
	- Hinostroza Quispe, Gianlucas Amed	
	- Ipanaque Pazo, Jorge Paul
  - León Robles, Illary Marcelo

