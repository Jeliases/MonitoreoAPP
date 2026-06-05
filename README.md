Proyecto de Monitoreo de Unidades
Este proyecto consiste en una aplicación móvil desarrollada para la gestión y monitoreo en tiempo real de unidades de transporte. La solución permite a los usuarios autenticarse, visualizar la ubicación exacta de los vehículos mediante integración con mapas y gestionar notificaciones de estado.

Tecnologías y Herramientas Utilizadas
Para el desarrollo de esta aplicación se han implementado las siguientes tecnologías y herramientas, siguiendo las mejores prácticas de la industria:

Lenguaje: Kotlin

Arquitectura: MVVM (Model-View-ViewModel) para la separación de responsabilidades y la mantenibilidad del código.

UI Framework: Jetpack Compose, utilizado para la construcción de interfaces declarativas y modernas.

Networking: Retrofit con Gson, para el consumo de servicios RESTful y la gestión de respuestas JSON.

Mapas: Google Maps SDK for Android, permitiendo la visualización geoespacial de la flota en tiempo real.

Asincronía: Corrutinas de Kotlin y StateFlow para la gestión de procesos en segundo plano y el flujo de estados reactivos.

Gestión de Diseño: Se ha utilizado Zeplin como herramienta de referencia para la implementación fiel de la interfaz de usuario, asegurando la precisión en los componentes y layouts.

Tipografía: Se ha integrado la tipografía oficial del proyecto, Nunito e Inter, para garantizar la consistencia visual y mejorar la legibilidad en toda la interfaz, configuradas globalmente mediante el sistema de tipografía de Compose.

Características Principales
Autenticación de Usuarios: Sistema de inicio de sesión seguro con consumo de API.

Monitoreo en Tiempo Real: Visualización de unidades mediante marcadores personalizados con rotación y estado dinámico.

Centro de Notificaciones: Sistema de alertas para el seguimiento detallado de operaciones.

Navegación Intuitiva: Flujo de navegación optimizado entre el mapa, facturación, perfil y notificaciones.
