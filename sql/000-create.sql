CREATE TABLE  `codeanalyzer`.`JClass` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fullyQualifiedName` varchar(230) NOT NULL,
  `simpleName` varchar(230) NOT NULL,
  `sourcePath` varchar(350) DEFAULT NULL,
  `sourceFile` varchar(230) DEFAULT NULL,
  `packageName` varchar(230) DEFAULT NULL,
  `packageId` int(10) unsigned DEFAULT NULL,
  `organization` varchar(230) DEFAULT NULL,
  `superclassName` varchar(230) DEFAULT NULL,
  `superclassId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `JClass_IDX_PackageName` (`packageName`,`simpleName`) USING BTREE,
  KEY `JClass_IDX_SuperclassName` (`superclassName`)
) ENGINE=InnoDB AUTO_INCREMENT=5177 DEFAULT CHARSET=utf8;

CREATE TABLE `codeanalyzer`.`JInterface` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `interfaceName` VARCHAR(230) NOT NULL,
  `sourcePath` VARCHAR(230),
  `sourceFile` VARCHAR(230),
  `packageName` VARCHAR(230),
  `packageId` INTEGER UNSIGNED,
  `superclassName` VARCHAR(230),
  `superclassId` INTEGER UNSIGNED,
  PRIMARY KEY (`id`),
  INDEX `JInterface_IDX_interfaceName`(`interfaceName`)
)
ENGINE = InnoDB;

CREATE TABLE `codeanalyzer`.`JClassImplementsJInterface` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `jclass_id` INTEGER UNSIGNED NOT NULL,
  `interfaceName` VARCHAR(250) NOT NULL,
  `jinterface_id` INTEGER UNSIGNED,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB;


-- Verify index length vs field length for text columns.
-- Plan out how I'm going to make a second pass over the table to build normalized data for packages/etc

