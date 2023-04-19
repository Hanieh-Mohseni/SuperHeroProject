


INSERT INTO `superhero`
VALUES
	(1,'Catwoman','Wiley, agile thief. Wears a black bodysuit and stylized mask'),
	(2,'Batman','Long time foe: Joker. Parents died when he was young. very rich.'),
	(3,'Black Canary','Skilled at hand to hand combat. Black Canary is an inherited title'),
	(4,'Storm','Control over the weather, affiliated with the X-Men. Fights for justice at all times.'),
	(5,'Spider-man','Bit by a radioactive spider and somehow survived. Not Likely to Leave New York City');
INSERT INTO `power`
	VALUES
		(1,'Weather Manipulation'),(2,'Canary Cry'),(3,'Rich'),
        (4,'Cat-Like Abilities'),(5,'Spider-Like Abilities');
INSERT INTO `superpower`
	VALUES
		(4,1),(3,2),(2,3),(1,4),(5,5);
INSERT INTO `location`
	VALUES
		(1,'Walgreens','Walgrees in Middle Country NY','2215 Middle Country RD Centerreach, NY 11720',-73.0901219,40.8589643),
        (2, 'Costco Wholesale', null, '20 Bridewell Pl, Clifton, NJ 07014', 40.847119403819846, -74.11014779004233),
        (3, 'Ladies First Fitness & Spa','Best Mud Mask', '42015 10th St W, Lancaster, CA 93534',34.69936070798236, -118.0103837740711);
INSERT INTO  `organization`
	VALUES
		(1, 'Justice League Of America', null),
        (2, 'X-Men', null),
        (3, 'Galactic Alliance of Spider-Men', null),
        (4, 'Avengers', null),
        (5, 'Spider-Army/Web-Warriors', null),
        (6, 'Birds Of Prey', null),
        (7, 'Gotham City Sirens', null),
        (8, 'Injustice League', null);