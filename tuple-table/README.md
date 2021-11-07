#Inspiration
## J2EE Design Patterns: Patterns in the Real World

### The Tuple Table Pattern <i>(interpreted from the book)</i>
The Tuple table pattern stores an object in the database in a highly flexible format that can be manipulated at the 
database level by normal human beings and can be easily extended without having to convert existing data.  Much of the
data used in an enterprise application can be condensed into sets of fields.
We can use this approach to persist objects to the database by assigning each object instance a primary key and then
storing labeled values for each key (a tuple).

<i>The primary advantage of this approach is extreme flexibility. By storing object fields as name and value pairs, we 
can modify te application to include new fields without changing the underlying database structure.

The primary disadvantage of the tuple table pattern is in its integrity enforcement. Relational databased are very 
good at enforcing rules at the column level, but aren't so good at enforcing rules as the data level.</i>



Create an instance of MariaDB before executing this code in local

````
CREATE TABLE IF NOT EXISTS object_data (
OBJ_PK int NOT NULL,
FIELDNAME varchar(20) NOT NULL,
NUMERICAL int,
STRING varchar(255)
);
````

Field  |  Type | Null | Key | Default | Extra |
--- | --- | --- | --- |--- |--- |
OBJ_PK    | int(11)      | NO   |     | NULL    |       |
FIELDNAME | varchar(20)  | NO   |     | NULL    |       |
NUMERICAL | int(11)      | YES  |     | NULL    |       |
STRING    | varchar(255) | YES  |     | NULL    |       |

##To run the project of tuple table
Simply executed after checkout as 
````
mvn clean test
````
