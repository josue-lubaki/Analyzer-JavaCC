## Analyzer-JavaCC
### Les différents objectifs à atteindre dans ce projet sont donnés dans ce qui suit :
- Analyse de la portée des attributs des classes d’un programme : Pour chaque
classe du programme analysé, donnez les statistiques descriptives suivantes : 
  - % des attributs publics, 
  - % des attributs protégés et 
  - % des attributs privés.
- Analyse de la visibilité entre classes d’un programme : Pour chaque classe
du programme analysé, on s’intéresse au type de ses attributs à travers lequel
nous pouvons avoir une idée sur la visibilité (permanente) entre objets :
  - % des attributs de type simple et 
  - % des attributs de référence (objet)
- Donnez, pour chaque classe du programme, la liste des classes vis-à-vis
  desquelles elle a une visibilité permanente.
- Reverse Engineering - Extraction du modèle objet (diagramme de classes) : Il
  s’agira d’analyser le programme dans le but d’extraire, par reverse engineering,
  le diagramme de classes UML (modèle intégrant les classes et les relations entre
  classes). On se limitera aux deux relations : Héritage et Association (l’agrégation
  étant une forme particulière d’association).
- Analyse de la dépendance entre classes d’un programme - Couplage entre
  classes : Nous nous intéressons dans ce contexte au couplage entre les différentes
  classes du programme analysé relativement aux appels entre méthodes : Il
  faudra compter le nombre de fois qu’une classe Ci appelle (via ses méthodes) les
  méthodes d’une classe Cj. Donnez pour chaque classe Ci du programme analysé
  la liste des classes qu’elle appelle (avec le nombre d’appels).
- Extraction du graphe d’appels directs entre méthodes d’un programme : Il
  faudra, pour chaque méthode d’une classe, donner la liste des méthodes (et leur
  classe d’appartenance) qu’elle appelle directement (appels directs uniquement –
  ne pas considérer les appels indirects).