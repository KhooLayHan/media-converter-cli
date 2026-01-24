# Media Converter CLI

An extensible Command Line Interface (CLI) tool for converting data formats involving JSON, CSV, XML.

**Coursework:** Design Patterns (Level 3)  
**Student Name:** Khoo Lay Han   
**Quality Attribute:** Maintainability

---

## Overview

This project demonstrates the application of software design patterns to improve the **Maintainability** of a Java application. It contains two distinct implementations for comparison purposes:

1.  **Refined Solution (The "Good" App):** A modular architecture using Strategy, Factory, Template Method, and Facade patterns.
2.  **Legacy Solution (The "Bad" App):** A monolithic, procedural implementation used as a baseline for empirical evaluation.

## Tech Stack

- **Language:** Java 21 Temurin
- **Build Tool:** Gradle and Groovy DSL
- **CLI Framework:** Picocli
- **JSON/XML/CSV Processing:** Jackson (3.0.0)
- **Logging:** SLF4J + Logback 
- **Code Quality:** Checkstyle, SpotBugs, Spotless with Google Java Format

## Prerequisites
Before running the application, ensure you have the following installed:
- Java Development Kit (JDK) 21 
- Gradle 8.14 
- Terminal/Command Prompt/Powershell

## Quick Setup Guide

### 1. Clone and Configure
   Clone the repository. 
```bash
git clone https://github.com/KhooLayHan/media-converter-cli
cd media-converter-cli

# Copy the users.json file from the template
cp .users.example.json .users.json
```

### 2. Build the Project
This command compiles the code, runs tests, and performs quality checks (Spotless/Checkstyle).
```bash
./gradlew clean build
```

### 3. Run the Refined (Pattern-Based) Solution
You can run the application directly using the custom Gradle task configured in `build.gradle`.

**Default Run (JSON to CSV):**
```bash
./gradlew runPattern
```

**Custom Arguments:**
```bash
# Syntax: ./gradlew runPattern --args="-i <input_file> -o <output_file>"
./gradlew runPattern --args="-i users.csv -o users.xml"
```

### 4. Run the Legacy (Simpler) Solution
To observe the baseline application used for the empirical evaluation:
```bash
./gradlew runLegacy
```

### 5. Running via JAR (Production Mode)
To create a standalone "Fat JAR" (including all dependencies):
```bash
./gradlew shadowJar
```
Then run it anywhere:
```bash
java -jar build/libs/media-converter-cli-1.0-SNAPSHOT-all.jar -i users.json -o users.xml
```