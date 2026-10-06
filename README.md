# Petits cadeaux pour Grands Effets (PGE)

Projet réalisé en binôme dans le cadre du module **CSC4102 – Conception et
programmation orientées objet** de Télécom SudParis (semestre de printemps 2026), avec
[Ozgur Tas](mailto:ozgur.tas@telecom-sudparis.eu).

L'application gère les cadeaux de fin d'année offerts par un comité d'entreprise
aux enfants des familles de salariés : chaque enfant dispose d'un budget de
points avec lequel il réserve des cadeaux en stock limité.

## Ce que fait le système

- gestion des **familles**, des **enfants** et du **catalogue de cadeaux** (ajout, retrait, listage) ;
- **réservation** de cadeaux par les enfants, dans la limite de leurs points et du stock disponible ;
- **réassort** des cadeaux et **notification** des familles lorsqu'un cadeau redevient disponible
  (publication/abonnement via `SubmissionPublisher`) ;
- règles de gestion vérifiées par des préconditions, avec une exception métier `OperationImpossible`.

## Démarche et livrables

Le projet suit une démarche de conception dirigée par la spécification, documentée dans [CONCEPTION.md](CONCEPTION.md) :

1. cas d'utilisation, priorités, préconditions et postconditions ;
2. préparation des tests de validation ;
3. conception : classes candidates, diagrammes de classes et de séquence (PlantUML, dans [`Diagrammes/`](Diagrammes)) ;
4. machines à états, invariants de classe et fiches de classes (`Famille`, `Cadeau`) ;
5. tests unitaires et de validation ;
6. suivi par les enseignants dans [`Suivi/readme.md`](Suivi/readme.md).

## Technologies

Java 21, Maven, JUnit 5 (tests unitaires paramétrés), Awaitility (tests asynchrones),
Checkstyle et SpotBugs, Javadoc, PlantUML, intégration continue GitLab.

## Structure

```
src/main/java/.../pge/        PGE (façade), Famille, Enfant, Cadeau, Reservation, ConsommateurNotification
src/test/java/.../unitaires/  tests unitaires des classes métier
src/test/java/.../validation/ tests de validation, un par cas d'utilisation
Diagrammes/                   diagrammes UML (sources .pu et exports)
CONCEPTION.md                 spécification et conception détaillées
```

## Compiler et tester

```bash
mvn clean verify
```

Les dépendances `csc4102-util` et `tsp-csc-config-checkstyle` sont servies par le dépôt Maven du
cours (voir `pom.xml`). Si ce dépôt n'est plus accessible, la compilation échouera à la résolution des dépendances.

## Ma contribution

Projet mené en binôme, avec un travail réparti sur toutes les phases (spécification, conception,
implémentation, tests). Mes contributions principales, visibles dans l'historique git (72 commits) :

**Spécification et conception**
- rédaction des préconditions et postconditions et des tables de décision pour la préparation des tests de validation ;
- diagramme de classes (relations, navigabilité `Cadeau`/`Réservation`, composition `Enfant`/`Réservation`) ;
- diagrammes de séquence : ajout d'une réservation, ajout d'un enfant, ajout d'un cadeau ;
- machines à états, invariants et fiches des classes `Famille` et `Cadeau`, et préparation des tests unitaires associés ;
- prise en compte des remarques de suivi des enseignants (révision des diagrammes, des conditions et du modèle,
  par ex. ajout de l'attribut `nbInitial` en plus de `nbDisponible` pour détecter les réservations en cours).

**Implémentation (Java)**
- classe `Cadeau` et classe `Reservation` ;
- l'essentiel de la façade `PGE` : ajout et retrait de cadeaux, de familles et de réservations, opérations de listage,
  réassort des cadeaux ;
- système de **notifications** : abonnement des familles à la disponibilité d'un cadeau, notification des membres du CE
  et des familles lors d'un retrait ou d'un retour en stock (`ConsommateurNotification`, `SubmissionPublisher`) ;
- participation aux classes `Famille` et `Enfant`, avec mon binôme.

**Tests**
- tests unitaires de `Cadeau` ;
- la grande majorité des tests de validation, un par cas d'utilisation : ajout d'un enfant, d'un cadeau, d'une réservation ;
  retrait d'une famille, d'un enfant, d'un cadeau, d'une réservation ; listage des enfants ; demande de notification ;
  réassort, y compris les tests des notifications et des postconditions ;
- les tests unitaires de `Famille` et `Enfant` ont été écrits par mon binôme.

**Démarche collaborative**
Travail en binôme sur GitLab avec branches (`develop`, branches de tâches par sprint), fusions et résolution de conflits,
et intégration continue.

## Crédits et licence

- Squelette du projet, classe `PGE` et bibliothèque `csc4102-util` : équipe enseignante CSC4102 (Denis Conan, Élisabeth Brunet, Sophie Chabridon).
- Binôme : Yasmine Belmaaziz, Ozgur Tas.
- Licence : GNU LGPL v3 ou ultérieure, voir [LICENSE.txt](LICENSE.txt).
