Ce fichier contiendra les remarques faites lors des suivis. Un nouveau
suivi est indiqué par une nouvelle section datée.

Certaines remarques demandent des actions de votre part, vous les
repérez par une case à cocher.

- []  Action (à réaliser) 

Merci de nous indiquer que vous avez pris en compte la remarque en
cochant la case. N'hésitez pas à écrire dans ce fichier et à nous
exposer votre point de vue.

- [x] Action (réalisée)
    - RÉPONSE et éventuelles remarques de votre part, 

 
---
# Suivi du lun. 16 févr. 2026 18:57:26
Sophie Chabridon
 
Préambule : pour rappel, les messages d'erreur avec leurs explications
sont à l'adresse suivante :
- https://gitlabense.imtbs-tsp.eu/enseignants-csc4102/csc4102-exemples/-/blob/main/Suivi/messages_pour_le_suivi.md?ref_type=heads

Remarque générale : très bon démarrage ; quelques remarques à prendre en compte mais continuez ainsi !

- [x] GEN-08-Noms-Prénoms
  rajouter vos noms et prénoms au début du readme
  
## Spécification et préparation des tests de validation

### Diagrammes de cas d'utilisation

- [x] GEN-09-Titre-manquant-diagramme
  toujours donner un titre aux figures et aux tables

- [x] Plusieurs cas d'usage pour "lister" les éléments sont accessibles au moins par le SRH et les membres du CE

### Préconditions et postconditions

1. Cas d'utilisation « Ajouter un cadeau »

- [x] PREPOSTCOND-07-Pb-formulation-d-un-terme
  Vous considérez la description d'un cadeau comme étant unique, cela n'est pas nécessairement le cas. Il est préférable d'utiliser un attribut spécifique pour identifier un cadeau. 

2. Cas d'utilisation « Ajouter un enfant »

Point d'attention : Vous avez choisi de ne pas définir d'attribut « identificateur » pour un enfant.
Il faudra donc veiller à utiliser plusieurs attributs pour identifier un enfant de manière unique (identificateur de la famille, nom et prénom de l'enfant...). Cela peut complexifier le système.

3. Cas d'utilisation « Ajouter une réservation d'un cadeau »

- [x] PREPOSTCOND-01-Pré-post-condition-manquante
  Il faut également vérifier l'existence de la famille.

- [x] PREPOSTCOND-07-Pb-formulation-d-un-terme
  Certaines conditions concernent plusieurs attributs, ce qui rend difficile la vérification et la détermination du nombre de tests de validation nécessaires. 
  
### Tables de décision des tests de validation

1. Cas d'utilisation « Ajouter un cadeau »

- [x] TABLEDECTV-07-MAJ-précondition-postcondition
  Mettre en conformité avec les conditions.

2. Cas d'utilisation « Ajouter un enfant »
ok

3. Cas d'utilisation « Ajouter une réservation d'un cadeau »

- [x] TABLEDECTV-07-MAJ-précondition-postcondition
  Mettre en conformité avec les conditions.
 
---
# Suivi du jeu. 26 févr. 2026 23:22:48
Elisabeth Brunet
 
Bon travail! Néanmoins revoyez précisement le diagramme de séquence de UC3. 
 
 
### Diagramme de classes

- [x] Vous pouvez encore affiner vos associations multiples en précisant s'il
  s'agit d'agregation ou de composition.
    - ajout d'une composition
  
- [x] La navigabilité de Cadeau vers Réservation est-elle vraiment utile?
    - étant donné qu'elle n'est pas utile, la navigabilité a été restreinte


### Diagrammes de séquence

1. Cas d'utilisation « Ajouter un cadeau »

- ok

2. Cas d'utilisation « Ajouter un enfant à une famille »

- [x] D'après votre diagramme de classes, vous n'avez pas accès à
Enfant depuis PGE. Vous devez chercher l'enfant depuis Famille, et non
depuis PGE. Toute la suite se fait dans Famille. 

3. Cas d'utilisation « Ajouter une réservation de cadeaux pour un enfant »

- [x] il faut d'abord chercher la famille, puis l'enfant dans la
  famille. Ainsi, vous avez besoin de l'idFamille et idEnfant en
  paramètre. Revoyez vos pré et post-conditions pour ce UC dans ce sens.
  - les pré/ post conditions ont été mises à jour en ce sens, et le diagramme de séquence aussi
  
- [x] la façade n'est qu'un point d'entrée dans le système et doit en
  faire le moins possible. Il faut qu'elle délègue aux objets
  concernés. Vous devriez "creer Résrevation", "incrementerQuantité",
  ect depuis Enfant et non depuis PGE. Nous pouvons en rediscuter en
  séance si cela n'est pas clair.  

### Raffinement du diagramme de classes

1. Fiche de la classe « Classe »

- [] 

### Diagramme de machine à états et invariant

1. Diagramme de machine à états de la classe « Cadeau »

- [x] Suivant si vous modifier la navigabilité entre Reservation et
  Cadeau, faites attention à comment savoir s'il existe des
  réservation de ce cadeau qui bloquerait sa mise en destruction.
  - ajout de l'attribut nbInitial en plus de l'attribut nbDisponible pour palier la restriction de la navigabilité Cadeau/Réservation

2. Invariant de la classe « Cadeau »

- [X] 

## 4. Préparation des tests unitaires

1. Table de décision des tests unitaires de la méthode Cadeau::constructeur

- [X] à faire

2. Table de décision des tests unitaires de la méthode Cadeau::réserver (ou équivalent)

- [X] à faire
 
 
---
# Suivi du mer. 18 mars 2026 08:48:23
Elisabeth Brunet
 

Très bon travail!! Quelques petits retours pour encore améliorer votre
code ! Bravo!


### Programmation de la solution

#### Classes du diagramme de classes avec leurs attributs

- [X] Raffinez la navigabilité de vos associations dans votre diagramme
  de classes. D'après ce dernier vous devriez avoir une navigabilité
  de Enfant vers Famille que vous n'avez pas dans votre code (version
  à conserver).


#### Méthodes des cas d'utilisation de base

1. Cas d'utilisation « Ajouter un cadeau »

- [X] Le bloc 
if (cadeaux.get(idCadeau) != null) {
			throw new OperationImpossible("cadeau déjà existant avec id=" + idCadeau);
		}
		 doit être mis après avoir vérifier que idCadeau est non non
		 vide.
		 
		 
		 

2. Cas d'utilisation « Ajouter un enfant à une famille »

- [X] Comme vous avez un identifiant unique en paramètre, la recherche
  de l'Enfant dans la collection peut être faire avec un get plutôt
  qu'avec un boucle d'itération comme vous le faites dans chercherEnfant.

Remarque : Nous utilisons une ArrayList pour lister les enfants d'une Famille et non une HashMap car on suppose qu'une famille ne contient pas un nombre trop élévé d'enfant. Ceci explique l'utilisation d'une boucle plutôt qu'une méthode get().

3. Cas d'utilisation « Ajouter une réservation de cadeaux pour un enfant »

- [X] Nickel!


## Programmation et exécution des tests

### Tests de validation des cas d'utilisation

1. Cas d'utilisation « Ajouter un cadeau »

- [X] ok

2. Cas d'utilisation « Ajouter un enfant à une famille »

- [X] ok

3. Cas d'utilisation « Ajouter une réservation de cadeaux pour un enfant »

- [X] ok

### Tests unitaires des méthodes d'une classe

Remarque : La méthode réserver est associé dans l'énoncé à la classe 'Cadeau'. Néanmoins, nous avons ajouter en plus de la classe 'Cadeau' (sans que cela ne soit demandé) des tests unitaires pour le constructeur de la classe 'Enfant'. Si cet ajout est améné à poser des problèmes nous la retireront pour nous contenter uniquement des attentdus. 

Pour l'instant sommes en attente de la validation de cette partie.

1. Constructeur de la classe `Enfant`

- [] ok

2. Méthode `réserver` (ou équivalent) de la classe `Enfant`

- [] à faire


Questions partie Notification (séance 9): 

- Dans le diagramme de classes, faut-il faire implémenter une interface à              ConsommateurNotification ou bien cela n'est pas nécessaire ?


- Comment représenter dans le diagramme de classes le fait que le                      ConsommateurNotification est un objet utilisé par l'acteur «Membre d'une famille », faut-il une association ou une note (ou rien) ?