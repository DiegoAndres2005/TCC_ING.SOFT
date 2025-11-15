# TRABAJO COLABORATIVO CONTEXTUALIZADO – INGENIERÍA DE SOFTWARE


**Título del Proyecto:
Diseño y desarrollo de un sistema de gestión de inventario mediante una API REST utilizando Spring Boot y MySQL para la tienda de barrio “El Buen Vecino”**




**Integrantes del CIPA:**

    ⦁	Yair Bravo Palomino 
    
    ⦁	Luis Alejandro Martinez Polo
    
    ⦁	Heiner Jose Morales Alvarado 
    
    ⦁	Diego Andres Martinez Conde


**Tutor:**
 Pedro Jose Consuegra Cañarete



**Asignatura:**
 Ingeniería de Software


**Fecha de entrega:**
 13/11/2025

 ---
 
## **Descripción del Proyecto**

**Contexto**


En Colombia, las tiendas de barrio y pequeños comercios representan una parte muy importante del comercio local. Son negocios familiares o de pequeños emprendedores que ofrecen productos básicos, víveres, bebidas, artículos de aseo y otros elementos necesarios para el día a día. Sin embargo, la mayoría de estas tiendas todavía manejan su inventario de manera manual, con cuadernos o en hojas de Excel. Esto genera muchos problemas de control y pérdidas económicas.
Nuestra propuesta busca dar una solución real y aplicable a este tipo de negocio. Tomamos como caso la tienda “El Buen Vecino”, un ejemplo típico de tienda de barrio, que actualmente atiende cerca de 150 clientes por día, pero sufre con los métodos tradicionales de control de stock.

**Situación problemática actual**

En esta tienda el inventario se controla con apuntes manuales y hojas de cálculo. Esto genera errores constantes, pérdida de tiempo y datos desactualizados. Cuando el dueño quiere saber cuánto stock tiene o qué productos están por agotarse, tiene que revisar todo manualmente, lo que retrasa las decisiones y hace que se pierdan ventas.
A continuación, se resumen las principales problemáticas detectadas:

  1.	Control manual del inventario: El dueño usa cuadernos y hojas de Excel. Esto causa errores humanos, pérdida de información y diferencias entre lo que hay físicamente y lo que aparece en los registros.
  2.	Desconocimiento del stock en tiempo real: No se puede saber con precisión qué productos hay disponibles o cuáles están por acabarse, lo que provoca que el cliente pida un producto que “parece” estar disponible, pero no lo está.
  3.	Productos vencidos: Al no tener control automatizado de fechas, se pierden productos por vencimiento, causando pérdidas entre $200.000 y $300.000 mensuales.
  4.	Falta de análisis de ventas: No se identifican los productos más vendidos ni los de baja rotación. Esto afecta las decisiones sobre qué surtir o qué dejar de comprar.
  5.	Gestión ineficiente de proveedores: No hay registros organizados de las compras realizadas, ni comparación de precios o historial de proveedores.
  6.	Conteos físicos largos: Se necesitan entre 8 y 10 horas para hacer un conteo total de los productos, y aun así se cometen errores.
  7.	Dificultad para crecer: Sin un sistema digital, el negocio no puede abrir otra sucursal ni delegar responsabilidades sin perder control.



**Necesidad identificada**

Ante esta situación, se necesita implementar un sistema digital de gestión de inventario que permita automatizar el control de los productos, conocer el stock en tiempo real, generar reportes, gestionar proveedores y reducir pérdidas por vencimiento o errores humanos.
Además, el sistema debe ser fácil de usar, accesible desde un computador o tablet, y con una interfaz simple que se comunique con una API REST desarrollada en Java (Spring Boot) y base de datos MySQL.



**Viabilidad del proyecto**

El sistema es completamente viable porque:

  -	El negocio ya cuenta con un computador.
  -	Los empleados tienen conocimientos básicos en tecnología.
  -	El costo de desarrollo es bajo.
  -	No requiere modificar la forma en que trabaja la tienda, solo digitalizarla.
  -	El retorno de inversión se calcula entre 3 y 6 meses.
  
---

## **Objetivos del Proyecto Objetivo general**

Diseñar e implementar un sistema de gestión de inventario mediante una API REST desarrollada con Spring Boot y base de datos MySQL, aplicando la metodología ágil Scrum y los principios de la ingeniería de software, con el fin de optimizar el control de inventario, ventas y compras en la tienda de barrio “El Buen Vecino”.

**Objetivos específicos**

    1.	Analizar y levantar los requisitos funcionales y no funcionales del sistema mediante entrevistas y observación directa en la tienda.
    2.	Diseñar la arquitectura del sistema utilizando el patrón en capas y principios SOLID.
    3.	Desarrollar los módulos principales de productos, inventario, ventas y proveedores.
    4.	Implementar alertas automáticas para productos próximos a vencer o con stock bajo.
    5.	Crear reportes de ventas e inventario que apoyen la toma de decisiones.
    6.	Desplegar el sistema con contenedores Docker.
    7.	Documentar el proyecto con diagramas UML y pruebas en Postman.

---

## **Justificación del Proyecto**

**Justificación técnica**

El desarrollo aplica los conocimientos aprendidos en la asignatura, como:

     ●	Arquitectura en capas (Controller, Service, Repository)
     ●	Uso del framework Spring Boot
     ●	Principios SOLID y Clean Code
     ●	Metodología ágil Scrum
     ●	Pruebas unitarias con JUnit
     ●	Persistencia con JPA/Hibernate
     ●	Despliegue en Docker
     ●	Control de versiones con GitHub

**Justificación social y académica**

El proyecto mejora la calidad de vida del propietario al reducir su carga de trabajo y permite a los empleados aprender sobre herramientas digitales.
Desde el punto de vista académico, el proyecto permite aplicar de forma práctica lo aprendido y demuestra la capacidad del grupo para resolver problemas reales con la ingeniería de software.

**4.	Elicitación y Requisitos del Sistema Técnicas de elicitación**
   
        ●	Entrevista con el propietario.
        ●	Observación directa del trabajo diario.
        ●	Revisión de los cuadernos y registros actuales.
        ●	Análisis de necesidades de control de stock, compras y ventas.


**Requisitos funcionales principales**

    RF1 - Gestión de Artículos: El sistema debe permitir añadir, editar y eliminar artículos del inventario.
    
    RF2 - Registro de Ventas: El sistema debe permitir registrar ventas de artículos y actualizar el stock.
    
    RF3 - Gestión de Proveedores: El sistema debe permitir añadir, editar y eliminar proveedores, con validación de artículos asociados.
    
    RF4 - Visualización de Inventario: El sistema debe mostrar una tabla de artículos con nombre, cantidad, precio, valor total, proveedor y estado.
    
    RF5 - Visualización de Estadísticas de Ventas: El sistema debe mostrar métricas clave y gráficos de ventas.
 
**Requisitos no funcionales**

    RNF1 - Rendimiento: El sistema debe responder a las interacciones del usuario en menos de 2 segundos.
    
    RNF2 - Usabilidad: La interfaz de usuario debe ser intuitiva y fácil de usar.
    
    RNF3 - Fiabilidad: El sistema debe asegurar la persistencia segura de todos los datos.
    
    RNF4 - Escalabilidad: El sistema debe soportar un gran volumen de datos de inventario, proveedores y ventas.
    
    RNF5 - Mantenibilidad: El código debe ser estructurado, comentado y fácil de modificar.


**Reglas de Negocio**

        1.	No se pueden registrar productos duplicados.
        2.	El stock nunca puede ser negativo.
        3.	Los movimientos de inventario no se eliminan, solo se ajustan.
        4.	Los proveedores deben tener un NIT único.

---

## **Diseño Arquitectónico del Proyecto**

El sistema se diseñó con una arquitectura multicapa, donde cada capa tiene una responsabilidad clara y separada. Esto ayuda a mantener el código ordenado y fácil de mejorar o ampliar.

**Estructura de capas**

  -	Controller: recibe las peticiones del cliente (API REST).
  -	Service: contiene la lógica del negocio.
  -	Repository: maneja la comunicación con la base de datos.
  -	Model: define las entidades (Producto, Venta, Proveedor, etc.).
 
**Diagramas UML del Proyecto**

A continuación, se presentan los principales diagramas del sistema desarrollado en PlantUML.

**a.	Diagrama de Casos de Uso**

<p align="center">
  <img src="https://github.com/user-attachments/assets/cf774001-0fb0-4a45-9b48-d6910929fcef" alt="image" width="716" height="577"/>
</p>

Este diagrama representa las funcionalidades principales disponibles para el usuario dentro del sistema de gestión de inventario y ventas. Organiza las acciones en cinco módulos clave:

  -	Interfaz y Navegación: permite mostrar mensajes y moverse entre secciones.
  
  -	Estadísticas: ofrece visualización de estadísticas de ventas.
  
  -	Ventas: incluye registro de ventas y consulta del historial.
  
  - Proveedores: gestiona la creación, edición, eliminación y visualización de proveedores.
  
  -	Inventario: permite agregar, editar, eliminar y consultar artículos.

El usuario tiene acceso directo a todas estas funciones, lo que refleja una interfaz centralizada y amigable para la gestión operativa del negocio.

 
**b.	Diagrama Entidad–Relación (ERD)**

<p align="center">
  <img src="https://github.com/user-attachments/assets/fafb1dd9-30c5-421f-9a70-47cd01097fe4" alt="image" width="544" height="448" style="border-radius: 20px;" />
</p>

El diagrama entidad-relación representa la estructura de la base de datos para wl sistema de gestión de inventario, donde el Producto es la entidad central conectada a `CategoríaProducto` y `Proveedor` mediante relaciones muchos a uno, y a `MovimientoInventario`, `Venta` y `OrdenCompra` mediante relaciones uno a muchos. Cada entidad contiene atributos clave como identificadores, nombres, precios y fechas, y las relaciones reflejan operaciones comerciales típicas como el registro de ventas, control de stock, y gestión de proveedores, todo basado en requisitos funcionales específicos del sistema.
 
**c.	Diagrama de componentes**

<p align="center">
  <img src="https://github.com/user-attachments/assets/cd0a2c28-8a6f-452b-955e-7d356ded2f4d" alt="image" width="735" height="759" style="border-radius: 20px;" />
</p>

Este diagrama muestra la arquitectura de un sistema backend desarrollado con Spring Boot, donde un cliente (como Postman o un navegador) envía solicitudes HTTP en formato JSON. Estas solicitudes son procesadas por el backend a través de una estructura en capas: Controller (recibe la petición), Service (gestiona la lógica de negocio), Repository (accede a la base de datos usando JPA/Hibernate), y Entities/Models (definen las estructuras de datos). Finalmente, la información se almacena o consulta en una base de datos MySQL, completando el flujo de comunicación entre el cliente y el sistema. 

**d.	Diagrama de Despliegue**

<p align="center">
  <img src="https://github.com/user-attachments/assets/47d065c7-bbb7-4975-8de7-30f434d0f7a9" alt="image" width="739" height="731" style="border-radius: 20px;" />
</p>

Este diagrama representa la arquitectura de una aplicación web donde un usuario local, desde un computador o tablet, accede al sistema mediante Postman o navegador web, enviando peticiones HTTP/HTTPS. Estas solicitudes llegan al servidor o entorno en la nube, donde se ejecuta la aplicación dentro de un contenedor Docker que aloja una API REST desarrollada con Spring Boot. Esta API se conecta a una base de datos MySQL utilizando JDBC, permitiendo la gestión eficiente de datos en un entorno moderno y escalable.

---

**Metodología de Desarrollo**

Se utilizó la metodología Scrum, que permite trabajar por sprints y entregar avances funcionales en cada iteración.
-	Roles definidos:
    -	Product Owner: Heiner.
    - Scrum Master: Luis.
    -	Development Team: Yair, Diego.
-	Stacks:
    -	IDEs: VScode, IntelliJ IDEA.
    -	Lenguajes:
    -	Frontend:
      -	`HTML` `CSS` `JavaScript` .
    - Backend:
      - `Java` + **Sprint Boot**
    -	Incremento: Funcionalidad entregable.
-	Eventos Scrum:
    - Sprint Planning, Daily Scrum, Review y Retrospective.
Cada sprint tuvo una duración de 2 semanas, entregando avances como el módulo de productos, inventario, alertas y ventas.


**Indicadores de Éxito**

-	El sistema se despliega correctamente en Docker con MySQL.
-	Se logran pruebas unitarias con cobertura superior al 70%.
-	Los endpoints funcionan correctamente en Postman.
-	La base de datos se encuentra normalizada.
-	El código cumple con principios SOLID y Clean Code.
-	Los reportes y alertas operan correctamente.


**Resultados Esperados**

-	Disminución de pérdidas por vencimiento de productos.
-	Control del inventario en tiempo real.
-	Mejora en la toma de decisiones.
-	Incremento en la rentabilidad del negocio.
-	Ahorro de tiempo en conteos manuales.



**Conclusiones**

El desarrollo de este sistema demuestra que es posible aplicar la ingeniería de software para resolver problemas reales de la comunidad. Gracias al uso de tecnologías modernas como Spring Boot, Docker y MySQL, se logró una solución funcional, escalable
 
y económica. Además, el trabajo en equipo bajo la metodología Scrum permitió organizar las tareas y mantener un ritmo constante de avances.

El sistema propuesto no solo ayuda al dueño de la tienda “El Buen Vecino”, sino que puede adaptarse fácilmente a otros negocios como farmacias, ferreterías o minimercados. Esto demuestra que la tecnología, bien aplicada, puede mejorar los procesos incluso en negocios tradicionales y pequeños.

---

# **Bibliografía**

-   Sommerville, I. (2016). Ingeniería de Software (10ª ed.). Pearson.
-	Schwaber, K., & Sutherland, J. (2020). The Scrum Guide: The Definitive Guide to Scrum.
-	Martin, R. C. (2008). Clean Code: A Handbook of Agile Software Craftsmanship. Prentice Hall.
-	Spring.io (2025). Spring Boot Documentation. https://spring.io/projects/spring-boot
-	MySQL	Documentation	(2025).	MySQL	Reference	Manual. https://dev.mysql.com/doc/
-	Docker Inc. (2025). Docker Overview. https://docs.docker.com
-	PlantUML (2025). Herramienta para la creación de diagramas UML mediante código. Dispon
