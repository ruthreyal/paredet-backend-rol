# 🚀 Despliegue de Backend - ParedetApp

Este repositorio contiene el backend del proyecto **ParedetApp**, una tienda online de papel pintado y fotomurales, desarrollado con Spring Boot y PostgreSQL.

---

## ✅ Checklist antes de hacer push y redeploy en Railway

Este checklist te ayudará a evitar errores en el despliegue automático en Railway.

---

### 🛠️ 1. Verifica que el proyecto compila localmente

```bash
cd backend
mvn clean install -DskipTests
```

❗ Si da error, **no hagas push** hasta corregirlo.

---

### 🔍 2. Comprueba que el backend funciona en local

```bash
java -jar target/ParedetApp-0.0.1-SNAPSHOT.jar
```

Después visita en el navegador:

```
http://localhost:8080/actuator/health
```

---

### 🧹 3. Asegúrate de no subir archivos ignorados

Confirma que no hay `.jar`, `/target`, ni `node_modules/` en staging:

```bash
git status
```

Si aparecen:

```bash
git reset HEAD archivo
```

---

### 💬 4. Resume los cambios con un buen mensaje de commit

```bash
git add .
git commit -m "🚀 Login funcionando + control acceso con JWT"
```

---

### ☁️ 5. Haz push a tu rama de desarrollo (por ejemplo, `dev`)

```bash
git push origin dev
```

---

### ✅ 6. Verifica que Railway hace deploy automático

En Railway > pestaña **Deployments**, asegúrate de ver el commit más reciente.

---

### 🧪 7. Prueba el backend desplegado

```bash
https://<tu-backend>.railway.app/actuator/health
```

Luego, prueba login, registro y otros endpoints con Postman o frontend.

---

### 📌 Documentación de la API

Consulta todos los endpoints disponibles:

➡️ [Ver lista completa de endpoints](./ENDPOINTS.md)

---

### 🧘 Consejo extra

> Si algo falla, **no empieces a modificar código sin antes revisar los logs en Railway**. A veces es solo una variable mal escrita o un fallo de red.