# 📱 Spendee

Application Android de gestion de dépenses, développée en **Kotlin** avec **Jetpack Compose** et **Room**.

## 🚀 Fonctionnalités

- ➕ Ajouter une dépense (montant + catégorie)
- 🗑 Supprimer une dépense
- 📊 Voir les statistiques par période
- 🏠 Écran d’accueil avec la liste des dépenses du mois
- 💾 Persistance locale avec Room

## 🛠️ Tech Stack

- **Langage** : Kotlin
- **UI** : Jetpack Compose + Material 3
- **Navigation** : Navigation Compose
- **Architecture** : MVVM (ViewModel + Repository + DAO)
- **Base de données** : Room
- **Coroutines & Flow** : gestion asynchrone & réactive

## 🧪 Tests

Deux types de tests sont inclus :

### ✅ Unit tests (JVM)

- Localisés dans `src/test/java/...`
- Exemple : `SpendRepositoryTest`
- Utilise un **DAO factice** en mémoire (pas de Room, pas d’Android)
- Vérifie la logique métier du repository



## ▶️ Exécution

1. Cloner le projet
2. Ouvrir dans **Android Studio** (Arctic Fox ou +)
3. Compiler et lancer sur émulateur ou appareil Android (minSdk 24)

## 📌 Notes

- L’application est une démo simple mais extensible.

- Les tests sont **courts mais démonstratifs** : ils prouvent qu’il existe des tests unitaires et instrumentés.

  