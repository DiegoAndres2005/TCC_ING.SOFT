TRABAJO COLABORATIVO CONTEXTUALIZADO – INGENIERÍA DE SOFTWARE



Título del Proyecto:
Diseño y desarrollo de un sistema de gestión de inventario mediante una API REST utilizando Spring Boot y MySQL para la tienda de barrio “El Buen Vecino”




Integrantes del CIPA:


Yair Bravo Palomino Luis Alejandro Martinez Polo Heiner Jose Morales Alvarado Diego Andres Martinez Conde



Tutor:
Pedro Jose Consuegra Cañarete



Asignatura:
Ingeniería de Software


Fecha de entrega:
13/11/2025
 
1.	Descripción del Proyecto

Contexto


En Colombia, las tiendas de barrio y pequeños comercios representan una parte muy importante del comercio local. Son negocios familiares o de pequeños emprendedores que ofrecen productos básicos, víveres, bebidas, artículos de aseo y otros elementos necesarios para el día a día. Sin embargo, la mayoría de estas tiendas todavía manejan su inventario de manera manual, con cuadernos o en hojas de Excel. Esto genera muchos problemas de control y pérdidas económicas.
Nuestra propuesta busca dar una solución real y aplicable a este tipo de negocio. Tomamos como caso la tienda “El Buen Vecino”, un ejemplo típico de tienda de barrio, que actualmente atiende cerca de 150 clientes por día, pero sufre con los métodos tradicionales de control de stock.



Situación problemática actual

En esta tienda el inventario se controla con apuntes manuales y hojas de cálculo. Esto genera errores constantes, pérdida de tiempo y datos desactualizados. Cuando el dueño quiere saber cuánto stock tiene o qué productos están por agotarse, tiene que revisar todo manualmente, lo que retrasa las decisiones y hace que se pierdan ventas.
A continuación, se resumen las principales problemáticas detectadas:

1.	Control manual del inventario: El dueño usa cuadernos y hojas de Excel. Esto causa errores humanos, pérdida de información y diferencias entre lo que hay físicamente y lo que aparece en los registros.
2.	Desconocimiento del stock en tiempo real: No se puede saber con precisión qué productos hay disponibles o cuáles están por acabarse, lo que provoca que el cliente pida un producto que “parece” estar disponible, pero no lo está.
 
3.	Productos vencidos: Al no tener control automatizado de fechas, se pierden productos por vencimiento, causando pérdidas entre $200.000 y $300.000 mensuales.
4.	Falta de análisis de ventas: No se identifican los productos más vendidos ni los de baja rotación. Esto afecta las decisiones sobre qué surtir o qué dejar de comprar.
5.	Gestión ineficiente de proveedores: No hay registros organizados de las compras realizadas, ni comparación de precios o historial de proveedores.
6.	Conteos físicos largos: Se necesitan entre 8 y 10 horas para hacer un conteo total de los productos, y aun así se cometen errores.
7.	Dificultad para crecer: Sin un sistema digital, el negocio no puede abrir otra sucursal ni delegar responsabilidades sin perder control.



Necesidad identificada

Ante esta situación, se necesita implementar un sistema digital de gestión de inventario que permita automatizar el control de los productos, conocer el stock en tiempo real, generar reportes, gestionar proveedores y reducir pérdidas por vencimiento o errores humanos.
Además, el sistema debe ser fácil de usar, accesible desde un computador o tablet, y con una interfaz simple que se comunique con una API REST desarrollada en Java (Spring Boot) y base de datos MySQL.



Viabilidad del proyecto

El sistema es completamente viable porque:
●	El negocio ya cuenta con un computador.

●	Los empleados tienen conocimientos básicos en tecnología.

●	El costo de desarrollo es bajo.
 
●	No requiere modificar la forma en que trabaja la tienda, solo digitalizarla.

●	El retorno de inversión se calcula entre 3 y 6 meses.


Justificación social y económica
El proyecto tiene un impacto más allá de la tienda “El Buen Vecino”. Sirve como modelo para miles de tiendas similares en todo el país. Su implementación mejora la competitividad, fomenta la formalización económica y reduce el desperdicio de productos. También promueve el empleo y ayuda a profesionalizar pequeños negocios que muchas veces no acceden a herramientas tecnológicas por desconocimiento o falta de recursos.

2.	Objetivos del Proyecto Objetivo general
Diseñar e implementar un sistema de gestión de inventario mediante una API REST desarrollada con Spring Boot y base de datos MySQL, aplicando la metodología ágil Scrum y los principios de la ingeniería de software, con el fin de optimizar el control de inventario, ventas y compras en la tienda de barrio “El Buen Vecino”.


Objetivos específicos
1.	Analizar y levantar los requisitos funcionales y no funcionales del sistema mediante entrevistas y observación directa en la tienda.

2.	Diseñar la arquitectura del sistema utilizando el patrón en capas y principios SOLID.


3.	Desarrollar los módulos principales de productos, inventario, ventas y proveedores.


4.	Implementar alertas automáticas para productos próximos a vencer o con stock bajo.
 
5.	Crear reportes de ventas e inventario que apoyen la toma de decisiones.


6.	Desplegar el sistema con contenedores Docker.


7.	Documentar el proyecto con diagramas UML y pruebas en Postman.




3.	Justificación del Proyecto


Justificación técnica

El desarrollo aplica los conocimientos aprendidos en la asignatura, como:

●	Arquitectura en capas (Controller, Service, Repository)
●	Uso del framework Spring Boot
●	Principios SOLID y Clean Code
●	Metodología ágil Scrum
●	Pruebas unitarias con JUnit
●	Persistencia con JPA/Hibernate
●	Despliegue en Docker
●	Control de versiones con GitHub

Justificación económica

El costo estimado del primer año es de unos $410.000 COP, incluyendo hosting y dominio. En cambio, los beneficios mensuales pueden llegar a $1.000.000 COP, gracias a la reducción de pérdidas, mejor control del stock y optimización de compras.
 
Esto significa un retorno de inversión (ROI) muy alto y una recuperación en menos de 15 días.

Justificación social y académica
El proyecto mejora la calidad de vida del propietario al reducir su carga de trabajo y permite a los empleados aprender sobre herramientas digitales.
Desde el punto de vista académico, el proyecto permite aplicar de forma práctica lo aprendido y demuestra la capacidad del grupo para resolver problemas reales con la ingeniería de software.

4.	Elicitación y Requisitos del Sistema Técnicas de elicitación
●	Entrevista con el propietario.
●	Observación directa del trabajo diario.
●	Revisión de los cuadernos y registros actuales.
●	Análisis de necesidades de control de stock, compras y ventas.


Requisitos funcionales principales
●	CRUD completo de productos, categorías, proveedores y ventas.
●	Control de entradas, salidas y ajustes de inventario.
●	Generación automática de alertas por stock bajo o productos próximos a vencer.
●	Reportes de ventas y análisis de productos de alta o baja rotación.
●	Gestión de órdenes de compra a proveedores.
●	Cálculo automático de márgenes de ganancia.
 
Requisitos no funcionales
●	Respuesta rápida (menos de 2 segundos en consultas).
●	Seguridad de datos con variables de entorno y validaciones.
●	Código limpio y mantenible.
●	Arquitectura escalable y portable con Docker.
●	Compatibilidad multiplataforma (Windows/Linux).
●	Documentación y pruebas completas en Postman.




5.	Reglas de Negocio

1.	No se pueden registrar productos duplicados.
2.	El precio de venta siempre debe ser mayor que el de compra.
3.	El stock nunca puede ser negativo.
4.	Los movimientos de inventario no se eliminan, solo se ajustan.
5.	Las ventas no pueden registrarse sin stock suficiente.
6.	Los proveedores deben tener un NIT único.
7.	Las órdenes de compra solo se pueden cerrar si todos los productos fueron recibidos.


6.	Diseño Arquitectónico del Proyecto

El sistema se diseñó con una arquitectura multicapa, donde cada capa tiene una responsabilidad clara y separada. Esto ayuda a mantener el código ordenado y fácil de mejorar o ampliar.

Estructura de capas
●	Controller: recibe las peticiones del cliente (API REST).
 
●	Service: contiene la lógica del negocio.
●	Repository: maneja la comunicación con la base de datos.
●	Model: define las entidades (Producto, Venta, Proveedor, etc.).
 
7.	Diagramas UML del Proyecto
A continuación, se presentan los principales diagramas del sistema desarrollado en PlantUML.

a.	Diagrama de Casos de Uso

 
b.	Diagrama Entidad–Relación (ERD)

 
c.	Diagrama de componentes

 
d.	Diagrama de Despliegue

<img width="769" height="751" alt="image" src="https://github.com/user-attachments/assets/47d065c7-bbb7-4975-8de7-30f434d0f7a9" />


8.	Metodología de Desarrollo
Se utilizó la metodología Scrum, que permite trabajar por sprints y entregar avances funcionales en cada iteración.
•	Roles definidos:
o	Product Owner: Encargado de priorizar requisitos.
o	Scrum Master: Coordina las actividades del equipo.
o	Development Team: Grupo de programadores.
•	Artefactos:
o	Product Backlog: Lista de funcionalidades.
 
o	Sprint Backlog: Tareas del sprint actual.
o	Incremento: Funcionalidad entregable.
•	Eventos Scrum:
o	Sprint Planning, Daily Scrum, Review y Retrospective.
Cada sprint tuvo una duración de 2 semanas, entregando avances como el módulo de productos, inventario, alertas y ventas.


9.	Indicadores de Éxito
•	El sistema se despliega correctamente en Docker con MySQL.
•	Se logran pruebas unitarias con cobertura superior al 70%.
•	Los endpoints funcionan correctamente en Postman.
•	La base de datos se encuentra normalizada.
•	El código cumple con principios SOLID y Clean Code.
•	Los reportes y alertas operan correctamente.



10.	Resultados Esperados
•	Disminución de pérdidas por vencimiento de productos.
•	Control del inventario en tiempo real.
•	Mejora en la toma de decisiones.
•	Incremento en la rentabilidad del negocio.
•	Ahorro de tiempo en conteos manuales.



11.	Conclusiones
El desarrollo de este sistema demuestra que es posible aplicar la ingeniería de software para resolver problemas reales de la comunidad. Gracias al uso de tecnologías modernas como Spring Boot, Docker y MySQL, se logró una solución funcional, escalable
 
y económica. Además, el trabajo en equipo bajo la metodología Scrum permitió organizar las tareas y mantener un ritmo constante de avances.

El sistema propuesto no solo ayuda al dueño de la tienda “El Buen Vecino”, sino que puede adaptarse fácilmente a otros negocios como farmacias, ferreterías o minimercados. Esto demuestra que la tecnología, bien aplicada, puede mejorar los procesos incluso en negocios tradicionales y pequeños.





12.	Repositorio en GitHub: (Enlace aqui)
En este repositorio se puede ver cómo se estructuró la aplicación con Spring Boot y MySQL, el código de cada módulo (productos, ventas, proveedores, alertas), los archivos Docker para el despliegue, y las colecciones de pruebas con Postman. De esta manera, cualquier persona puede clonar el proyecto, ejecutarlo y comprobar su funcionamiento real.


13.	Bibliografía
•	Sommerville, I. (2016). Ingeniería de Software (10ª ed.). Pearson.
•	Schwaber, K., & Sutherland, J. (2020). The Scrum Guide: The Definitive Guide to Scrum.
•	Martin, R. C. (2008). Clean Code: A Handbook of Agile Software Craftsmanship. Prentice Hall.
•	Spring.io (2025). Spring Boot Documentation. https://spring.io/projects/spring-boot
•	MySQL	Documentation	(2025).	MySQL	Reference	Manual. https://dev.mysql.com/doc/
•	Docker Inc. (2025). Docker Overview. https://docs.docker.com
•	GitHub Inc. (2025). Repositorio del Proyecto: Sistema de Gestión de Inventario "El Buen Vecino". Recuperado de: [Pegar aquí enlace al repositorio GitHub]
•	PlantUML (2025). Herramienta para la creación de diagramas UML mediante código. Dispon
