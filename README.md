# Levantar el servicio de base de datos

```
  docker-compose up -d
```

- Verificar que el contenedor esté corriendo
```
  docker ps
```

# Endpoint para obtener una receta.
Este enpoint sirve para todos los roles (Médico, farmacéutico y paciente). 
## Solicitud GET

```
  http://localhost:8080/prescription/encrypted/<ID_RECETA>
```
Si la receta no ha sido surtida, el valor del farmaceutico será null.
Entonces hay que validar en frontend esa parte antes de mostrar los datos.

## RESPONSE
```
{
    "accessKey": "ORj9a5OCTchcRBMl/8OuojP7QMcATPBHvV9JmLgxeHRNyM08zFtRe6KCNX4=",
    "publicKeyServidor": "ZLHT8Zd3Ag8lUTdnh9T6645KXVuIga9iFT+4PbfaLgA=",
    "encryptedPrescription": <Archivo Cifrado>,
    "paciente": {
        "usuario": {
            "id": "8545d730-975f-48a2-81fe-865e425ca4d5",
            "email": "serge.martinez@example.com",
            "nombre": "Serge Eduardo Martínez Ramírez",
            "fechaNacimiento": "2003-02-12"
        },
        "matricula": "2025175945",
        "curp": "MARS030212HDFRFRD3"
    },
    "medico": {
        "usuario": {
            "id": "b777a235-d302-4c68-a232-dc5b7b8f5e99",
            "email": "doctor.juan@example.com",
            "nombre": "Dr. Juan Pérez",
            "fechaNacimiento": "1987-03-11"
        },
        "especialidad": "Pediatría",
        "clinica": "Clínica San Rafael",
        "cedula": "12345678",
        "telefono": "5569847844"
    },
    "prescription": {
        "id": "21d0e6c3-6d0a-4d75-a58e-1cdb5e72f06f",
        "surtida": false,
        "firma_medico": "x7f0Chm7KwZQIgA7HuAWiZMzcjhs7uHAyZB7dbCC59YR+MDGLYLuNWsMPBwQu0FkI4Mv4+OawKZeHCMG8MH7CQ==",
        "fecha_emision": "2025-06-22",
        "firma_farmaceutico": null,
        "fecha_surtido": null
    }
}
```

# Usuario cede permisos para visualizar la receta a un farmacéutico.
## Descripción
El paciente podrá ver en su vista los farmacéuticos, tiene que seleccionar a uno y podrá generar una llave de acceso para que el paciente pueda visualizar su receta y posteriormente ser surtida. El paciente podrá quitar el acceso a la receta.

## Enpoint POST 
```
http://localhost:8080/patient/grant-access
```

## REQUEST

```
{
    "idReceta": "21d0e6c3-6d0a-4d75-a58e-1cdb5e72f06f",
    "idFarmaceutico": "b1060168-b395-476c-b97e-5cc6000acf40",
    "password": "Ser123+12"
} 
```

## RESPONSE
```
{
    "status": "success",
    "message": "Se ha creado una llave de acceso para el farmacéutico: b1060168-b395-476c-b97e-5cc6000acf40"
}
```

# Usuario elimina la llave de acceso de un farmacéutico
## Descripción
El paciente podrá ver en su receta médica qué farmacéutico tiene acceso a visualizar su receta médica.
Puede eliminar la llave de acceso una vez que haya sido surtida la receta médica.

## ENDPOINT DELETE
```
http://localhost:8080/patient/revoke-access?prescriptionId=21d0e6c3-6d0a-4d75-a58e-1cdb5e72f06f&pharmacistId=b1060168-b395-476c-b97e-5cc6000acf40
```
* Requiere Token de autenticación.

## Response

```
{
    "status": "success",
    "message": "Se ha eliminado la llave de acceso al farmacéutico con ID: b1060168-b395-476c-b97e-5cc6000acf40"
}
```

# Firma de Farmacéutico

## ENDPOINT POST

```
http://localhost:8080/prescription/258e90cc-8fb6-4163-a904-f5d576b75a80/fill
```
Se envían los siguientes datos




