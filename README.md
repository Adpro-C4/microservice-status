# microservice-status-tracking-order

### Penerapan refactoring

MODEL

1. Refactoring Extract Method:

Saya telah mengekstrak logika validasi status dari metode createStatus dan updateStatus 
ke dalam metode terpisah bernama validateStatus. Ini membantu mengurangi duplikasi kode 
dan meningkatkan keterbacaan.

2. Refactoring Replace Conditional Logic with Constants:

Anda telah mengganti logika if dalam metode isValidStatus dengan menggunakan konstanta 
VALID_STATUSES. Hal ini membuat kode lebih ringkas dan mudah dimengerti.