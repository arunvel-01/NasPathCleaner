# 📂 NAS Path Cleaner

A lightweight desktop application built using **JavaFX** and **Spring Boot** that helps you scan and clean up files from any NAS or local directory. You can filter files older than a selected date and delete them easily using a graphical interface.

---

## ✨ Features

- 📁 Browse and select any NAS or local folder  
- 📅 Pick a date to find all files **modified before** that date  
- 🧾 View matching files in a clean, sortable table  
- ✅ Select/Deselect individual files or all at once  
- 🗑️ Delete selected files with a single click  
- ⚙️ Runtime-configurable file mask (e.g., `*.log`, `report_??.txt`)  
- ☕ Built with Spring Boot for backend logic and JavaFX for modern UI  

---

## 🚀 Tech Stack

- 🧠 **Java 17**
- 🎨 **JavaFX 21**
- 🔧 **Spring Boot**
- 📦 **Maven**

---

## 🛠️ Build & Run

### ✅ Prerequisites

- JDK **17** or higher installed  
- Maven installed  
- JavaFX SDK **21** installed


---

## 🛠️ Build & Run

### ✅ Prerequisites

- JDK 17 installed
- Maven installed
- JavaFX SDK 21

### 📦 Build

mvn clean install

### 📦 Run

mvn javafx:run