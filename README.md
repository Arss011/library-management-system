# library-management-system
RESTful API for a Library Management System.

Nama: **Dicky Aris Munandar**

Project: **Library Management System**

Tech Stack: **Java Spring Boot**

## API Documentation Swagger
Dokumentasi API lengkap tersedia melalui Swagger UI. 
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

## Flowchart
### Entity Relationship Diagram
```mermaid
graph TD
    subgraph "User Management"
        A[User]
    end

    subgraph "Master Data"
        B[Category]
        C[Buku]
    end

    subgraph "Transaction"
        D[Peminjaman]
    end

    A -- "Melakukan" --> D
    C -- "Dipinjam" --> D
    B -- "Memiliki" --> C

    style A fill:#FFD700,stroke:#333,stroke-width:2px,color:#000
    style B fill:#00BFFF,stroke:#333,stroke-width:2px,color:#FFF
    style C fill:#00BFFF,stroke:#333,stroke-width:2px,color:#FFF
    style D fill:#FF6347,stroke:#333,stroke-width:2px,color:#FFF
```

### Application Flow Diagram
```mermaid
graph TD
    subgraph "Sistem Perpustakaan"
        subgraph "Client"
            C(Client/User)
        end

        subgraph "API Gateway"
            API[API Gateway]
        end

        subgraph "Service"
            subgraph "Service Autentikasi"
                AuthController["/api/auth"]
                AuthService[AuthService]
                UserDB[User Table]
            end

            subgraph "Service Buku & Kategori"
                BukuController["/api/buku"]
                CategoryController["/api/category"]
                BukuService[BukuService]
                CategoryService[CategoryService]
                BukuDB[Buku Table]
                CategoryDB[Category Table]
            end

            subgraph "Service Peminjaman"
                PeminjamanController["/api/peminjaman"]
                PeminjamanService[PeminjamanService]
                PeminjamanDB[Peminjaman Table]
            end
        end
    end

    %% Hubungan
    C -- "HTTP Request" --> API
    API -- "Route" --> AuthController
    API -- "Route" --> BukuController
    API -- "Route" --> CategoryController
    API -- "Route" --> PeminjamanController

    AuthController -- "Memanggil" --> AuthService
    AuthService -- "Mengakses" --> UserDB

    BukuController -- "Memanggil" --> BukuService
    BukuService -- "Mengakses" --> BukuDB
    BukuService -- "Mengakses" --> CategoryDB

    CategoryController -- "Memanggil" --> CategoryService
    CategoryService -- "Mengakses" --> CategoryDB

    PeminjamanController -- "Memanggil" --> PeminjamanService
    PeminjamanService -- "Mengakses" --> PeminjamanDB
    PeminjamanService -- "Mengakses" --> BukuDB
    PeminjamanService -- "Mengakses" --> UserDB

    %% Styling untuk visual yang lebih jelas
    style C fill:#FFFFE0,stroke:#333,stroke-width:2px
    style API fill:#B0E0E6,stroke:#333,stroke-width:2px
    style AuthController fill:#E6E6FA,stroke:#333,stroke-width:2px
    style BukuController fill:#E6E6FA,stroke:#333,stroke-width:2px
    style CategoryController fill:#E6E6FA,stroke:#333,stroke-width:2px
    style PeminjamanController fill:#E6E6FA,stroke:#333,stroke-width:2px
    style AuthService fill:#F0FFF0,stroke:#333,stroke-width:2px
    style BukuService fill:#F0FFF0,stroke:#333,stroke-width:2px
    style CategoryService fill:#F0FFF0,stroke:#333,stroke-width:2px
    style PeminjamanService fill:#F0FFF0,stroke:#333,stroke-width:2px
    style UserDB fill:#FFF0F5,stroke:#333,stroke-width:2px
    style BukuDB fill:#FFF0F5,stroke:#333,stroke-width:2px
    style CategoryDB fill:#FFF0F5,stroke:#333,stroke-width:2px
    style PeminjamanDB fill:#FFF0F5,stroke:#333,stroke-width:2px
```
