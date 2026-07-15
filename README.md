# WhoDo - Mobile Android Application (Native Client)

Cliente móvil nativo desarrollado en **Java y Android SDK**, diseñado bajo un enfoque reactivo y robusto para interactuar de manera eficiente con el ecosistema de microservicios de **WhoDo**. 

La aplicación consolida la experiencia de usuario final para una plataforma de economía colaborativa, proporcionando interfaces fluidas, flujos de trabajo asíncronos y procesamiento seguro de transacciones monetarias en movilidad.

## 🚀 Arquitectura y Decisiones de Diseño

La arquitectura móvil fue estructurada priorizando la mantenibilidad, el bajo consumo de recursos y una respuesta reactiva ante eventos del servidor:

*   **Arquitectura Orientada a Eventos (Tiempo Real):** Integra el SDK de **Firebase Cloud Messaging (FCM)** para habilitar una comunicación bidireccional asíncrona. La aplicación reacciona de inmediato ante notificaciones push enviadas por el backend cuando ocurren cambios críticos en las órdenes de trabajo o conciliaciones de pago.
*   **Consumo Eficiente de APIs REST:** Implementa clientes HTTP estructurados para comunicarse de forma desacoplada con el backend de negocio (`whodo-workorder-service`) y financiero (`whodo-paymentorder-service`), aislando las llamadas de red del hilo principal de la interfaz de usuario (*UI Thread*).
*   **Módulos Transaccionales Integrados:** Incorpora vistas nativas de checkout interactuando de forma limpia con los flujos de pago web, capturando estados mediante listeners dinámicos para reflejar la confirmación transaccional de inmediato en el dispositivo.

## 🛠️ Stack Tecnológico

*   **Lenguaje:** Java (Desarrollo nativo bajo Android SDK)
*   **Entorno de Desarrollo:** Android Studio
*   **Mensajería y Eventos:** Firebase Cloud Messaging (FCM) / Push Notifications
*   **Componentes de UI:** Diseño responsivo con patrones nativos de Android, listas dinámicas y vistas asíncronas.
*   **Lógica de Negocio Local:** Sistema integrado de mensajería, reputación interna de usuarios y módulo de auditoría de estado.

## ⚙️ Características Técnicas Principales

1.  **Ecosistema de Notificaciones Reactivas:** Orquesta manejadores de servicios en segundo plano (`FirebaseMessagingService`) para procesar notificaciones incluso si la app está en segundo plano, sincronizando de forma segura los estados de las órdenes locales.
2.  **Ciclo de Vida Limpio (UI/UX Resiliente):** Gestión robusta del ciclo de vida de las *Activities* y *Fragments* para evitar fugas de memoria (*Memory Leaks*) durante la espera de respuestas asíncronas de la red o pasarelas de pago.
3.  **Arquitectura de Datos Desacoplada:** Diseñada para actuar como un cliente liviano, delegando las reglas duras de negocio y validación transaccional al backend y concentrándose en el procesamiento y renderizado fluido del payload recibido.

## 📁 Estructura del Proyecto

*   `activities/` / `fragments/`: Capa de presentación y vistas nativas interactivas.
*   `services/`: Componentes de segundo plano, incluyendo el receptor y parser de eventos de Firebase (FCM).
*   `network/` / `api/`: Manejo de peticiones HTTP distribuidas hacia los microservicios core.
*   `model/`: Objetos de transferencia de datos (DTOs) replicados de los contratos del backend.
