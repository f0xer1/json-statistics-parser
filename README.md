## Json statistics parser

### Unit 1 task: ProfITsoft

This project is a console program that collects statistics from JSON files and stores them in XML.

---
### Entity: Order

Main entity with attributes:

- **Order number**: Entity identifier.
- **Items** One or more items in the order.
- **Total amount** The full price of the order.
- **Customer**: Full customer name.

`The main entity user will be highlighted in the next project with its attributes.`

---
### Json object example

```
{
    "order_number":16008,
    "items":"Shoes, Tripod",
    "total_amount":2226.3,
    "customer":"John Doe"
}
```
---
## Examples of input and output files

An example of program execution.

### Input:

```
[
  {
    "order_number": 15261,
    "items": "Pants",
    "total_amount": 222.3,
    "customer": "David Lee"
  },
  {
    "order_number": 43828,
    "items": "Pants",
    "total_amount": 324.0,
    "customer": "Emily Williams"
  },
  {
    "order_number": 26112,
    "items": "Shoes, Tripod",
    "total_amount": 2226.3,
    "customer": "Michael Johnson"
  },
  {
    "order_number": 26132,
    "items": "Pants",
    "total_amount": 324.0,
    "customer": "Michael Johnson"
  }
]

```
### Output:
```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<statistics>
    <item>
        <value>David Lee</value>
        <count>1</count>
    </item>
    <item>
        <value>Emily Williams</value>
        <count>1</count>
    </item>
    <item>
        <value>Michael Johnson</value>
        <count>2</count>
    </item>
  
</statistics>
```
---
## Multithreading test
In the tests, a script is launched that runs the program from 1, 2, 4, 8 streams, the results are:

| Number of threads | Execution time, s |
| ----------- |-------------------|
| 1 | 0.5109196         |
| 2 | 0.1567543         |
| 4 | 0.0970044                |
| 8 | 0.0689988              |

The processing speed depends on the file size, but the higher the number of threads, the higher the efficiency

---
## Running

To run this application execute:
```bash 
mvn clean package
```
Then inside project directory execute:
```bash 
.\json-statistics-parser -a <your_attribute> -d <The directory path on your machine containing the JSON files> -t <NOT MANDATORY, the number of threads, if not added, will be set to the default number>
```
After successful execution, you should see a message that the statistics 
.xml file has been created in the same directory as your json files