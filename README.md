# library-management-system
RESTful API for a Library Management System.

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

    style A fill:#f9f,stroke:#333,stroke-width:2px
    style B fill:#ccf,stroke:#333,stroke-width:2px
    style C fill:#ccf,stroke:#333,stroke-width:2px
    style D fill:#f99,stroke:#333,stroke-width:2px
```

### Application Flow Diagram
```mermaid
graph TD
    subgraph "Library Management System"
        subgraph "Client"
            C(Client/User)
        end

        subgraph "API Gateway"
            API[API Gateway]
        end

        subgraph "Service Mikro (Microservices)"
            subgraph "Service Autentikasi"
                AuthController["/api/auth"]
                AuthService[AuthService]
                UserDB[User Database]
            end

            subgraph "Service Buku & Kategori"
                BukuController["/api/buku"]
                CategoryController["/api/category"]
                BukuService[BukuService]
                CategoryService[CategoryService]
                BukuDB[Buku Database]
                CategoryDB[Category Database]
            end

            subgraph "Service Peminjaman"
                PeminjamanController["/api/peminjaman"]
                PeminjamanService[PeminjamanService]
                PeminjamanDB[Peminjaman Database]
            end
        end
    end

    %% Definisikan hubungan antar node
    C -- "HTTP Request" --> API
    API -- "Request Routing" --> AuthController
    API -- "Request Routing" --> BukuController
    API -- "Request Routing" --> CategoryController
    API -- "Request Routing" --> PeminjamanController

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

    %% Styling untuk visual
    style C fill:#d4fcd7,stroke:#333,stroke-width:2px
    style API fill:#a8c7f9,stroke:#333,stroke-width:2px
    style AuthController fill:#f9f,stroke:#333,stroke-width:2px
    style BukuController fill:#f9f,stroke:#333,stroke-width:2px
    style CategoryController fill:#f9f,stroke:#333,stroke-width:2px
    style PeminjamanController fill:#f9f,stroke:#333,stroke-width:2px
    style AuthService fill:#ccf,stroke:#333,stroke-width:2px
    style BukuService fill:#ccf,stroke:#333,stroke-width:2px
    style CategoryService fill:#ccf,stroke:#333,stroke-width:2px
    style PeminjamanService fill:#ccf,stroke:#333,stroke-width:2px
    style UserDB fill:#f99,stroke:#333,stroke-width:2px
    style BukuDB fill:#f99,stroke:#333,stroke-width:2px
    style CategoryDB fill:#f99,stroke:#333,stroke-width:2px
    style PeminjamanDB fill:#f99,stroke:#333,stroke-width:2px
```
