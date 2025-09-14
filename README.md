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

### Alur Peminjaman Buku

```mermaid
graph TD
    %% Teks node yang lebih sederhana untuk menghindari kesalahan
    A[User Request: Pinjam Buku] --> B{Cari User & Buku};
    B -- "Ditemukan" --> C{Cek Ketersediaan & Stok};
    B -- "Tidak Ditemukan" --> G[Gagal: User/Buku tidak ditemukan];
    C -- "Ya" --> D[Kurangi Stok Buku];
    D --> E[Buat Record Peminjaman];
    E --> F[Sukses: Buku berhasil dipinjam];
    C -- "Tidak" --> H[Gagal: Buku tidak tersedia];

    %% Styling untuk visual
    style A fill:#4A90E2,stroke:#333,stroke-width:2px,color:#fff
    style B fill:#F5A623,stroke:#333,stroke-width:2px,color:#000
    style C fill:#F5A623,stroke:#333,stroke-width:2px,color:#000
    style D fill:#7ED321,stroke:#333,stroke-width:2px,color:#000
    style E fill:#7ED321,stroke:#333,stroke-width:2px,color:#000
    style F fill:#2ECC71,stroke:#333,stroke-width:2px,color:#fff
    style G fill:#E74C3C,stroke:#333,stroke-width:2px,color:#fff
    style H fill:#E74C3C,stroke:#333,stroke-width:2px,color:#fff
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
    style C fill:#4A90E2,stroke:#333,stroke-width:2px,color:#fff
    style API fill:#00A1D3,stroke:#333,stroke-width:2px,color:#fff
    style AuthController fill:#F5A623,stroke:#333,stroke-width:2px,color:#000
    style BukuController fill:#F5A623,stroke:#333,stroke-width:2px,color:#000
    style CategoryController fill:#F5A623,stroke:#333,stroke-width:2px,color:#000
    style PeminjamanController fill:#F5A623,stroke:#333,stroke-width:2px,color:#000
    style AuthService fill:#7ED321,stroke:#333,stroke-width:2px,color:#000
    style BukuService fill:#7ED321,stroke:#333,stroke-width:2px,color:#000
    style CategoryService fill:#7ED321,stroke:#333,stroke-width:2px,color:#000
    style PeminjamanService fill:#7ED321,stroke:#333,stroke-width:2px,color:#000
    style UserDB fill:#BD10E0,stroke:#333,stroke-width:2px,color:#fff
    style BukuDB fill:#BD10E0,stroke:#333,stroke-width:2px,color:#fff
    style CategoryDB fill:#BD10E0,stroke:#333,stroke-width:2px,color:#fff
    style PeminjamanDB fill:#BD10E0,stroke:#333,stroke-width:2px,color:#fff
```