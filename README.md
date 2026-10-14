# lista-reproduccion
Versión utilizada: Java JDK 21

# Integrantes
- Aviña Barrera Cristopher Isaac
- Decena Martinez Arturo
- Gómez Romero Jose Antonio
- Yllescas Bolaños Angel Xabeth


# Estructura del repositorio
```
lista-reproduccion/
├── README.md
└── src/
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
```
(También se puede descargar desde GitHub con **Code → Download ZIP** y descomprimir.)

# 2. Compilar
Desde la carpeta `lista-reproduccion` (la que contiene `src`):

```bash
mkdir out
javac -encoding UTF-8 -d out src/Cancion.java src/Nodo.java src/ListaReproduccion.java src/Menu.java src/Main.java src/PruebasLista.java
```
Los `.class` se generan en la carpeta `out`. Si no aparece ningún mensaje, la compilación fue exitosa.

# 3. Ejecutar el programa (menú)
```bash
java -cp out mx.unam.fesa.ico.Main
```

# 4. Ejecutar las pruebas
```bash
java -cp out mx.unam.fesa.ico.PruebasLista
```
Cada prueba muestra estado inicial, operación, resultado esperado y resultado obtenido.

# Nota
- Si se vuelve a compilar, se puede borrar la carpeta `out` y repetir el paso 2.