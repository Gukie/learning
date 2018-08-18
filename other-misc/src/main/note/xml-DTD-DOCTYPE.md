## refer
- https://xmlwriter.net/xml_guide/doctype_declaration.shtml (讲的很详细)


### DOCTYPE 的格式
```
<!DOCTYPE root_element [

Document Type Definition (DTD):
  elements/attributes/entities/notations/
  processing instructions/comments/PE references

]>
```


#### Private的 External DTD
```
<!DOCTYPE root_element SYSTEM "DTD_location">

```

#### public 的 External DTD
```$xslt
<!DOCTYPE root_element PUBLIC "DTD_name" "DTD_location">
```

where:

- DTD_location: relative or absolute URLglossary
- DTD_name: follows the syntax:
```
"prefix//owner_of_the_DTD//
  description_of_the_DTD//ISO 639_language_identifier"
```

The following prefixes are allowed in the DTD name:
|Prefix:	 |   Definition:|
|ISO 	|   The DTD is an ISO standard. All ISO standards are approved.|
|+	    |   The DTD is an approved non-ISO standard.|
|-	    |    The DTD is an unapproved non-ISO standard.|