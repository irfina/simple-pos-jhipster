# To-Do List

### Updated: 2024-05-26

#### 1. Sesuaikan UI Create & Update Province

- Format JSON untuk **create** dan **update** data propinsi adalah:

```json
{
    "countryIsoCode": <dua karakter kode ISO negara>,
    "name": <nama propinsi>
}
```

- Di tampilan **create** dan **update** propinsi yang ada saat ini, pilihan negara berupa combo-box (dropdown list)
  tapi yang muncul hanya kode ISO, jadi kurang user friendly. Jadi perlu dimodifikasi agar
  pilihan yang muncul formatnya \<nama negara\> \[\<kode ISO\>\], contoh: `Indonesia [ID]`.

#### 2. Rancang UI CRUD Product

- Informasi yang perlu diinputkan untuk **create** dan **update** data produk adalah:

  - Kategori produk
  - Nama produk
  - Kode (SKU) produk
  - Barcode produk
  - Harga jual
  - Deskripsi produk

- Jika format JSON yang saat ini belum sesuai untuk kebutuhan di atas, maka silakan diusulkan saja bagaimana formatnya,
  nanti saya sesuaikan.
