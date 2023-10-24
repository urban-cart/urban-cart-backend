import requests

url = "http://localhost:8080"
data = requests.post(f"{url}/auth/register", json={
  "email": "umangshrestha09@gmail.com",
  "password": "[Nimisha@1995#]",
  "confirmPassword": "[Nimisha@1995#]",
  "role": "USER"
}).json()
print(data)
data = requests.post(f"{url}/auth/login", json={
  "email": "umangshrestha09@gmail.com",
  "password": "[Nimisha@1995#]"
}).json()
print(data)


data = requests.get(f"{url}/auth/me", headers={
    "Authorization": f"Bearer {data['token']}"
}).json()

print(data)

