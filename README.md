# censo-habitantes
Esta aplicación de Android, desarrollada con Kotlin, gestiona un censo completo de los habitantes de la Tierra Media, permitiendo realizar operaciones CRUD en una base de datos SQLite. Es una herramienta diseñada para registrar y organizar información sobre las razas, profesiones y demás detalles de los habitantes de este mundo.

Características principales:
Gestión de datos de habitantes:
Los usuarios pueden agregar, modificar, eliminar y consultar información de los habitantes.

Filtrado y estadísticas:
Consulta el número total de habitantes por raza.
Genera listados dinámicos por profesión.

Interfaz dinámica:
Visualización de información en tablas creadas de forma programática.
Experiencia de usuario intuitiva y funcional.

Uso de SQLite:
Base de datos local optimizada para la persistencia de datos.

Estructura del proyecto:
Clase HabitantesSQLite: Maneja todas las operaciones de la base de datos, desde la creación de tablas hasta las consultas personalizadas.
Clase ListarProfesiones: Construye dinámicamente una tabla en la interfaz para mostrar profesiones y la cantidad de habitantes que las ejercen.

Tecnologías utilizadas:
Lenguaje: Kotlin.
Base de datos: SQLite.
Entorno: Android Studio.

Cómo funciona:
Registrar habitantes: El usuario puede ingresar detalles como nombre, raza y profesión.
Consultar datos: Se puede obtener información detallada sobre los habitantes, ya sea por raza o por profesión.
Actualizar o eliminar: Modifica los datos existentes o elimina registros según sea necesario.

Este proyecto busca ofrecer una herramienta práctica para registrar y analizar datos de la población como ejemplo de desarrollo con SQLite en Android.
