# Используем базовый образ Python
FROM python:3.12.0a7-slim-buster 

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы приложения в контейнер
COPY . /app

# Устанавливаем зависимости

RUN pip install  -r requirements.txt --no-cache-dir 
#&&
# \    apk del gcc musl-dev linux-headers 
#&& pip install psycopg2-binary
#RUN python setup.py build
#RUN pip install uvicorn

#CMD ["python", "main.py"]

# Определяем команду для запуска приложения
#CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8084"] 
CMD ["python", "main.py"] 
#CMD ["gunicorn ", "main:app", "--bind", "0.0.0.0:8084" ] 