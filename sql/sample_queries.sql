-- Count how many interfaces a given class implements. Sort by highest number implemented.
select JClass.fullyQualifiedName,
sum(IF(JClassImplementsJInterface.interfaceName is not null, 1, 0)) as Count_Interfaces
from JClass
left join JClassImplementsJInterface on JClassImplementsJInterface.jclass_id = JClass.id
group by JClass.id order by 2 desc;

-- Count how many classes are included from each organization. Sort by highest number.
select organization, count(*) as Count_Classes
from JClass
group by organization
order by 2 desc;

