# lista-reproduccion
Versión utilizada: Java JDK 21

# Integrantes
- Aviña Barrera Cristopher Isaac
- Nombre completo 2
- Nombre completo 3
- Nombre completo 4


# Estructura del repositorio
```
lista-reproduccion/
├── README.md
└── ico - copia/
    ├── Cancion.java
    ├── Nodo.java
    ├── ListaReproduccion.java
    ├── Menu.java
    ├── Main.java            <- programa principal
    └── PruebasLista.java    <- pruebas
```

# 1. Obtener el proyecto
```bash
git clone https://github.com/Isaac-Avina31/lista-reproduccion.git
cd lista-reproduccion
cd "ico - copia"
```
(También se puede descargar desde GitHub con **Code → Download ZIP**, descomprimir y abrir una terminal dentro de la carpeta `ico - copia`.)

## 2. Compilar
Desde dentro de la carpeta `ico - copia`:

```bash
mkdir out
javac -encoding UTF-8 -d out Cancion.java Nodo.java ListaReproduccion.java Menu.java Main.java PruebasLista.java
```
Los `.class` se generan en la carpeta `out`. Si no aparece ningún mensaje, la compilación fue exitosa.

## 3. Ejecutar el programa (menú)
```bash
java -cp out mx.unam.fesa.ico.Main
```

## 4. Ejecutar las pruebas
```bash
java -cp out mx.unam.fesa.ico.PruebasLista
```
Cada prueba muestra estado inicial, operación, resultado esperado y resultado obtenido.

## Notas
- Si la consola muestra mal los acentos en Windows, ejecutar antes `chcp 65001`.
- Si se vuelve a compilar, se puede borrar la carpeta `out` y repetir el paso 2.