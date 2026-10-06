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

> À COMPLÉTER : décris ici, en quelques lignes, ce que tu as réalisé toi-même
> (par ex. quelles classes, quels diagrammes, quels tests, quelles parties de la conception).

## Crédits et licence

- Squelette du projet, classe `PGE` et bibliothèque `csc4102-util` : équipe enseignante CSC4102 (Denis Conan, Élisabeth Brunet, Sophie Chabridon).
- Binôme : Yasmine Belmaaziz, Ozgur Tas.
- Licence : GNU LGPL v3 ou ultérieure, voir [LICENSE.txt](LICENSE.txt).
