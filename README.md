# News_management_maven
News Management 

Business requirements:  
 1. Develop web service for News Management system with the following entities: 
![image](https://github.com/Alexey-99/News_management_maven/assets/96728779/140934c4-653f-445d-9f66-c413553de0fb)

• All Name, Title and Content fields are required

• Created, Modified – have ISO 8601 format (wiki: ISO_8601). Example: 2018-08- 29T06:12:15.156. More discussion here: stackoverflow: how to get iso 8601. 

 2. The system should expose REST APIs to perform the following operations:
    
  − CRUD operations (what_is_CRUD) for News and Comment. If new tags and authors are passed during creation/modification – they should be created in the DB. 
  For update operation – update only fields, that pass in request, others should not be updated. Batch insert is out of scope. 
  
  − Get News:
  
      • get all o by Id o search (all params are optional and can be used in conjunction): 
         - by tag names and tag ids (many tags) 
         - by author name (one author) 
         - by part of Title 
