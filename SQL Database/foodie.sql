-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 07, 2021 at 12:34 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foodie`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_data`
--

CREATE TABLE `admin_data` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(150) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin_data`
--

INSERT INTO `admin_data` (`id`, `name`, `phone`, `email`, `username`, `password`) VALUES
(1, 'Admin 1', '087719274123', 'admin1@gmail.com', 'admin1', 'admin123'),
(2, 'Admin 2', '081293192738', 'admin2@gmail.com', 'admin2', 'admin123'),
(3, 'Admin 3', '081623172631', 'admin3@gmail.com', 'admin3', 'admin123'),
(4, 'Admin 4', '087312878111', 'admin4@gmail.com', 'admin4', 'admin123'),
(5, 'Admin 5 Edited', '08371283111', 'admin5edited@gmail.com', 'admin5', 'admin123'),
(6, 'Admin 6', '087731827812', 'admin6@gmail.com', 'admin6', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id_cart` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `food_name` varchar(150) NOT NULL,
  `number_order` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id_cart`, `id_user`, `food_name`, `number_order`) VALUES
(3, 2, 'Lobster', 1),
(4, 2, 'Ice Tea', 1),
(5, 3, 'Grilled Fish', 2),
(6, 3, 'CapChay', 1),
(7, 3, 'Beer', 1),
(8, 4, 'Beer', 1),
(9, 4, 'CapChay', 1),
(10, 4, 'Chicken Wing', 1),
(11, 6, 'Beer', 2),
(12, 6, 'CapChay', 3),
(13, 7, 'Beer', 3),
(14, 7, 'CapChay', 1),
(15, 1, 'CapChay', 1),
(16, 1, 'Beer', 1);

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `id_food` varchar(10) NOT NULL,
  `name` varchar(150) NOT NULL,
  `pic` varchar(100) NOT NULL,
  `description` varchar(250) NOT NULL,
  `id_category` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`id_food`, `name`, `pic`, `description`, `id_category`, `price`) VALUES
('F001', 'Beer', 'http://10.0.2.2/foodie/uploads/food/beer.png', 'Minuman beralkohol yang diproduksi melalui proses fermentasi bahan berpati tanpa melalui proses penyulingan setelah fermentasi', 1, 15000),
('F004', 'CapChay', 'http://10.0.2.2/foodie/uploads/food/capchay.png', 'Hidangan khas Tionghoa-Indonesia berupa banyak macam sayuran yang dimasak dengan cara direbus atau digoreng tumis', 2, 30000),
('F011', 'Chicken Wing', 'http://10.0.2.2/foodie/uploads/food/chicken_wing.png', 'Sebuah potongan dari sayap ayam yang diolah menjadi makanan', 4, 40000),
('F007', 'Grilled Fish', 'http://10.0.2.2/foodie/uploads/food/grilled_fish.png', 'Hidangan ikan yang dibakar atau dipanggang di atas api atau bara api. Hidangan ikan yang dibakar, muncul secara universal di berbagai belahan dunia', 3, 40000),
('F003', 'Ice Tea', 'http://10.0.2.2/foodie/uploads/food/ice_tea.png', 'Minuman yang mengandung kafeina, sebuah infusi yang dibuat dengan cara menyeduh daun, pucuk daun, atau tangkai daun yang dikeringkan dari tanaman Camellia sinensis dengan air panas', 1, 5000),
('F008', 'Lobster', 'http://10.0.2.2/foodie/uploads/food/lobster.png', 'Lobster bercapit membentuk sebuah keluarga dari crustacean besar laut. Mereka penting sebagai hewan, bisnis, dan makanan', 3, 50000),
('F002', 'Orange Juice', 'http://10.0.2.2/foodie/uploads/food/orange_juice.png', 'Sari buah jeruk adalah sari buah yang diperoleh dengan memeras atau menekan isi buah jeruk. Diminum di banyak tempat di dunia, sari buah jeruk biasanya menjadi bagian makan pagi atau sarapan.', 1, 10000),
('F012', 'Pork Belly', 'http://10.0.2.2/foodie/uploads/food/pork_belly.png', 'Potongan daging tanpa tulang dan berlemak dari perut babi', 4, 55000),
('F009', 'Prawn', 'http://10.0.2.2/foodie/uploads/food/prawn.png', 'Nama umum untuk krustasea air kecil dengan kerangka luar dan sepuluh kaki, beberapa di antaranya dapat dimakan.', 3, 40000),
('F006', 'Salad', 'http://10.0.2.2/foodie/uploads/food/salad.png', 'Jenis makanan yang terdiri dari campuran sayur-sayuran dan bahan-bahan makanan siap santap.', 2, 20000),
('F005', 'Spinach', 'http://10.0.2.2/foodie/uploads/food/spinach.png', 'Bayam jepang atau horenso adalah sayuran yang dimakan daunnya, dari genus Spinacia. Di Indonesia dapat dijumpai di supermarket dan kadang dikenal sebagai spinach, berbeda dengan bayam yang banyak dikenal di Indonesia sebagai \"spinach\"', 2, 20000),
('F010', 'Steak', 'http://10.0.2.2/foodie/uploads/food/steak.png', 'Sepotong daging sapi besar, biasanya daging sapi. Daging merah, dada ayam, dan ikan sering kali dipotong menjadi steik', 4, 55000);

-- --------------------------------------------------------

--
-- Table structure for table `order_cust`
--

CREATE TABLE `order_cust` (
  `id_order` int(11) NOT NULL,
  `order_code` varchar(20) NOT NULL,
  `id_user` int(11) NOT NULL,
  `food_name` varchar(150) NOT NULL,
  `number_order` int(11) NOT NULL,
  `order_date` varchar(20) NOT NULL,
  `total` double NOT NULL,
  `address` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_cust`
--

INSERT INTO `order_cust` (`id_order`, `order_code`, `id_user`, `food_name`, `number_order`, `order_date`, `total`, `address`) VALUES
(1, '6WPCGX', 1, 'Beer', 1, '27/11/2021', 58900, 'Jalan Kecana 13'),
(2, '6WPCGX', 1, 'CapChay', 1, '27/11/2021', 58900, 'Jalan Kecana 13'),
(9, '7DPAGI', 3, 'Beer', 1, '27/11/2021', 58900, 'Jalan Palem no 17'),
(10, '7DPAGI', 3, 'CapChay', 1, '27/11/2021', 58900, 'Jalan Palem no 17'),
(14, 'WZ4WQY', 6, 'Beer', 2, '29/11/2021', 135400, 'Jalan Kencana no 15'),
(15, 'WZ4WQY', 6, 'CapChay', 3, '29/11/2021', 135400, 'Jalan Kencana no 15'),
(16, 'UT5I0T', 7, 'Beer', 3, '29/11/2021', 89500, 'Jalan Satu no 15'),
(17, 'UT5I0T', 7, 'CapChay', 1, '29/11/2021', 89500, 'Jalan Satu no 15'),
(18, 'X648Y2', 8, 'Ice Tea', 4, '04/12/2021', 64000, 'Jalan Melati no 13'),
(19, 'X648Y2', 8, 'CapChay', 1, '04/12/2021', 64000, 'Jalan Melati no 13');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id_payment` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `order_code` varchar(20) NOT NULL,
  `payment_pic` varchar(150) NOT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT 0,
  `is_delivered` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id_payment`, `id_user`, `order_code`, `payment_pic`, `is_paid`, `is_delivered`) VALUES
(1, 1, '6WPCGX', 'http://10.0.2.2/foodie/uploads//transfer/6WPCGX.png', 1, 0),
(5, 3, '7DPAGI', 'http://10.0.2.2/foodie/uploads//transfer/7DPAGI.png', 1, 0),
(7, 6, 'WZ4WQY', 'http://10.0.2.2/foodie/uploads//transfer/WZ4WQY.png', 1, 1),
(8, 7, 'UT5I0T', 'http://10.0.2.2/foodie/uploads//transfer/UT5I0T.png', 1, 1),
(9, 8, 'X648Y2', 'http://10.0.2.2/foodie/uploads//transfer/X648Y2.png', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_data`
--

CREATE TABLE `user_data` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(150) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_data`
--

INSERT INTO `user_data` (`id`, `name`, `phone`, `email`, `username`, `password`) VALUES
(1, 'Andrew Brillyant', '08774821436', 'andrewbrillyant@gmail.co', 'andrewbrillyant', 'password123'),
(2, 'Kevin', '087719274818', 'kevin@gmail.com', 'kevin', 'password123'),
(3, 'Federico', '081946182731', 'federico@gmail.com', 'federico', 'password123'),
(4, 'Willy', '08123712631', 'willy@gmail.com', 'willy', 'password123'),
(6, 'Tika Edited', '087784188333', 'tikaedited@gmail.com', 'tika', 'password123'),
(7, 'Hans', '081723718271', 'hans@gmail.com', 'hans', 'password123'),
(8, 'Dummy Edited', '087748321333', 'dummyedited@gmail.com', 'dummy', 'password123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_data`
--
ALTER TABLE `admin_data`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id_cart`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `food_name` (`food_name`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `order_cust`
--
ALTER TABLE `order_cust`
  ADD PRIMARY KEY (`id_order`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `food_name` (`food_name`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id_payment`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `user_data`
--
ALTER TABLE `user_data`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_data`
--
ALTER TABLE `admin_data`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id_cart` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `order_cust`
--
ALTER TABLE `order_cust`
  MODIFY `id_order` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id_payment` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user_data`
--
ALTER TABLE `user_data`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user_data` (`id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`food_name`) REFERENCES `food` (`name`);

--
-- Constraints for table `order_cust`
--
ALTER TABLE `order_cust`
  ADD CONSTRAINT `order_cust_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user_data` (`id`),
  ADD CONSTRAINT `order_cust_ibfk_2` FOREIGN KEY (`food_name`) REFERENCES `food` (`name`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user_data` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
