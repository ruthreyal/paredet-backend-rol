# ✅ Checklist antes de hacer push y redeploy en Railway

Este checklist te ayudará a evitar errores en el despliegue de tu backend con Spring Boot en Railway.

---

## 🛠️ 1. Verifica que el proyecto compila localmente

```bash
cd backend
mvn clean install -DskipTests
```

🔹 Si da error, **no hagas push** hasta corregirlo.

---

## 🔍 2. Comprueba que el backend funciona en local

Ejecuta el `.jar` generado para asegurarte:

```bash
java -jar target/ParedetApp-0.0.1-SNAPSHOT.jar
```

Y abre en el navegador:

```
http://localhost:8080/actuator/health
```

---

## 🧹 3. Asegúrate de no subir archivos ignorados

Confirma que no hay archivos `.jar`, `/target`, ni `node_modules/` en staging:

```bash
git status
```

✅ Si están ahí, revisa tu `.gitignore` o haz:

```bash
git reset HEAD archivo
```

---

## 💬 4. Resume los cambios en un buen mensaje de commit

```bash
git add .
git commit -m "🚀 Login funcionando + control acceso con JWT"
```

---

## ☁️ 5. Haz push a tu rama de desarrollo (por ejemplo, `dev`)

```bash
git push origin dev
```

---

## 🚀 6. Verifica que Railway detecta el cambio y hace deploy

En Railway > Deployments, asegúrate de ver el nuevo commit con el mensaje que pusiste.

---

## 🧪 7. Prueba el backend desplegado

Usa:

```
https://<tu-backend>.railway.app/actuator/health
```

Y luego Postman o tu frontend para comprobar login, registro, etc.

---

## 🧘‍♀️ Consejo extra:

> Si algo falla, **no empieces a modificar código sin antes verificar los logs del deploy en Railway**. A veces el problema es una tontería como una variable mal escrita o una dependencia corrupta.

---