JPA enum with interface mapping
====

This example shows how to map an enum 'hierarchy', by using generics and `@MappedSuperclass`.

If you convert the mapping to entity inheritance mapping, it won't work anymore,  
to use the inherintance you have to use hibernate `@Type` or switch to JPA 2.1 converter.
