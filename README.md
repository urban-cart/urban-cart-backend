# Configure your environment before use:
```
export POSTGRES_DB=your_database_name
export POSTGRES_USER=your_username
export POSTGRES_PASSWORD=your_password
export POSTGRES_JDBC_URL=jdbc:postgresql://localhost:5432/${POSTGRES_DB}
export JWT_SECRET=your_secret
export JWT_REFRESH_SECRET=your_refresh_secret
export JWT_EXPIRATION=3600000  # 1 hour
export JWT_REFRESH_EXPIRATION=604800000 # 7 days
```
<u>note</u>: e JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size)
### Development Commands

| Command                     | Description                 |
|-----------------------------|-----------------------------|
| `$ ./gradlew bootRun`       | Run the development server. |
| `$ ./gradlew test`          | Run unit tests.             |
| `$ ./gradlew spotlessApply` | Apply code formatting.      |
| `$ ./gradlew check`         | Test code coverage.         |

If you want to see code coverage when test fails then run:
```bash
$ cd build/jacocoHtml/
$ python3 -m http.server
```

You can see report at http://localhost:8000/com.example.urbancart/
___


This project uses images that were downloaded from Unsplash, a platform with free-license images. I do not own these images, and they belong to their respective creators.

---
**Copyright Disclaimer:**

`All images used in this project are the property of their respective creators and are available on Unsplash under a free license. I do not claim ownership of these images. If you have any concerns regarding the use of specific images, please contact the creators directly on Unsplash.`
