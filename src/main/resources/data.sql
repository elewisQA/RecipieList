INSERT INTO recipe(RECIPE_NAME) VALUES ('Victoria Sponge');
INSERT INTO recipe(RECIPE_NAME) VALUES ('Shortbread');
INSERT INTO recipe(RECIPE_NAME) VALUES ('Ricks Rolls');
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Flour', 8, 'oz', 1);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Sugar', 8, 'oz', 1);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Butter', 8, 'oz', 1);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Eggs', 8, 'x', 1);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Vanilla Essence', 1, 'tsp', 1);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Butter', 6, 'oz', 2);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Sugar', 6, 'oz', 2);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Flour', 4, 'oz', 2);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('Yourself', 1, 'x', 3);
INSERT INTO ingredient(NAME, QUANTITY, UNIT, RECIPE_ID) VALUES ('A person (to never give up)', 1, 'x', 3);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Combine the sugar and butter', 'Step 1', 1);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Combine the sugar and butter', 'Step 1', 2);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Add in the flour', 'Step 2', 1);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Add in the flour', 'Step 2', 2);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Add in the egg', 'Step 3', 1);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Add in the Vanilla', 'Optional', 1);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Gonna give you up', 'Never', 3);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Gonna let you down', 'Never', 3);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Gonna run-around', 'Never', 3);
INSERT INTO step(DESCRIPTION, NAME, RECIPE_ID) VALUES('Dessert you ;)', 'And', 3);
