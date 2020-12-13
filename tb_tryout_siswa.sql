/*
SQLyog Ultimate v12.5.1 (64 bit)
MySQL - 10.4.14-MariaDB : Database - db_magnus
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `tb_tryout_siswa` */

DROP TABLE IF EXISTS `tb_tryout_siswa`;

CREATE TABLE `tb_tryout_siswa` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` int(10) unsigned NOT NULL,
  `id_tryout` int(10) unsigned NOT NULL,
  `id_jenis_tryout` int(10) unsigned NOT NULL,
  `id_jurusan` int(10) unsigned NOT NULL,
  `status` enum('aktif','selesai') DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `tb_tryout_siswa` */

insert  into `tb_tryout_siswa`(`id`,`id_user`,`id_tryout`,`id_jenis_tryout`,`id_jurusan`,`status`,`created_at`,`updated_at`) values (1,1,18,3,3164,'selesai','2020-11-09 12:38:37','2020-11-09 17:11:59');
insert  into `tb_tryout_siswa`(`id`,`id_user`,`id_tryout`,`id_jenis_tryout`,`id_jurusan`,`status`,`created_at`,`updated_at`) values (7,18,18,3,1,'selesai','2020-11-09 18:33:33','2020-11-09 19:39:14');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
