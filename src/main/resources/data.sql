

-- ----------------------------
-- Records of MstGroupMenu
-- ----------------------------
--SET IDENTITY_INSERT [dbo].[MstGroupMenu] ON
--    ;
--
--    INSERT INTO [dbo].[MstGroupMenu] ([CreatedBy], [CreatedDate], [IDGroup], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'1', NULL, NULL, N'User Management', N'Untuk Pengaturan Yang Berkaitan Dengan User')
--    ;
--
--    INSERT INTO [dbo].[MstGroupMenu] ([CreatedBy], [CreatedDate], [IDGroup], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-03-10 18:35:13.000000', N'2', NULL, NULL, N'Artikel', N'Group Menu Untuk Default User Setelah Registrasi')
--    ;
--
--    INSERT INTO [dbo].[MstGroupMenu] ([CreatedBy], [CreatedDate], [IDGroup], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-03-10 18:35:13.000000', N'3', NULL, NULL, N'staff manager', N'Group Menu Untuk staff manager')
--    ;
--
--    SET IDENTITY_INSERT [dbo].[MstGroupMenu] OFF
--    ;



--    -- ----------------------------
---- Records of MstMenu
---- ----------------------------
--    SET IDENTITY_INSERT [dbo].[MstMenu] ON
--    ;
--
--    INSERT INTO [dbo].[MstMenu] ([CreatedBy], [CreatedDate], [IDGroupMenu], [IDMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-03-10 18:34:26.000000', N'1', N'1', NULL, NULL, N'Group-Menu', N'/group-menu')
--    ;
--
--    INSERT INTO [dbo].[MstMenu] ([CreatedBy], [CreatedDate], [IDGroupMenu], [IDMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-03-10 18:34:47.000000', N'1', N'2', NULL, NULL, N'Menu', N'/menu')
--    ;
--
--    INSERT INTO [dbo].[MstMenu] ([CreatedBy], [CreatedDate], [IDGroupMenu], [IDMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-03-10 18:35:02.000000', N'1', N'3', NULL, NULL, N'Akses', N'/akses')
--    ;
--
--    INSERT INTO [dbo].[MstMenu] ([CreatedBy], [CreatedDate], [IDGroupMenu], [IDMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-03-10 18:35:13.000000', N'1', N'4', NULL, NULL, N'User', N'/user')
--    ;
--
--    INSERT INTO [dbo].[MstMenu] ([CreatedBy], [CreatedDate], [IDGroupMenu], [IDMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-03-10 18:35:13.000000', N'2', N'5', NULL, NULL, N'Artikel-1', N'/artikel-1')
--    ;
--
--    INSERT INTO [dbo].[MstMenu] ([CreatedBy], [CreatedDate], [IDGroupMenu], [IDMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-03-10 18:35:13.000000', N'2', N'6', NULL, NULL, N'Artikel-2', N'/artikel-2')
--    ;
--
--    SET IDENTITY_INSERT [dbo].[MstMenu] OFF
--    ;

-- ----------------------------
-- Records of MstAkses
-- ----------------------------
SET IDENTITY_INSERT [dbo].[MstAkses] ON
    ;

    INSERT INTO [dbo].[MstAkses] ([CreatedBy], [CreatedDate], [IDAkses], [ModifiedBy], [ModifiedDate], [NamaAkses], [Deskripsi]) VALUES (N'1', N'2025-03-10 18:33:19.000000', N'1', NULL, NULL, N'Admin', N'Administrator')
    ;

    INSERT INTO [dbo].[MstAkses] ([CreatedBy], [CreatedDate], [IDAkses], [ModifiedBy], [ModifiedDate], [NamaAkses], [Deskripsi]) VALUES (N'1', N'2025-03-10 18:33:35.000000', N'2', NULL, NULL, N'Member', N'Default Akses Dari Proses Registrasi')
    ;

    SET IDENTITY_INSERT [dbo].[MstAkses] OFF
    ;

---- ----------------------------
---- Records of MapAksesMenu
---- ----------------------------
--    INSERT INTO [dbo].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'1')
--    ;
--
--    INSERT INTO [dbo].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'2')
--    ;
--
--    INSERT INTO [dbo].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'3')
--    ;
--
--    INSERT INTO [dbo].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'4')
--    ;
--
--    INSERT INTO [dbo].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'5')
--    ;
--
--    INSERT INTO [dbo].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'6')
--    ;
--
--    INSERT INTO [dbo].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'2', N'5')
--    ;
--
--    INSERT INTO [dbo].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'2', N'6')
--    ;

-- ----------------------------
-- Records of MstProductCategory
-- ----------------------------
SET IDENTITY_INSERT [dbo].[MstProductCategory] ON
    ;

    INSERT INTO [dbo].[MstProductCategory] ([CreatedBy], [CreatedDate], [IDCategory], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'1', NULL, NULL, N'Furniture')
    ;

    INSERT INTO [dbo].[MstProductCategory] ([CreatedBy], [CreatedDate], [IDCategory], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'2', NULL, NULL, N'Elektronik')
    ;

    INSERT INTO [dbo].[MstProductCategory] ([CreatedBy], [CreatedDate], [IDCategory], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'3', NULL, NULL, N'Alat Tulis')
    ;

    SET IDENTITY_INSERT [dbo].[MstProductCategory] OFF
    ;

-- ----------------------------
-- Records of MstProduct
-- ----------------------------
SET IDENTITY_INSERT [dbo].[MstProduct] ON
    ;

    INSERT INTO [dbo].[MstProduct] ([CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'1', NULL, NULL, N'Kursi', N'Kursi warna abu-abu', N'1')
    ;

    INSERT INTO [dbo].[MstProduct] ([CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'2', NULL, NULL, N'Laptop', N'Laptop merek ACER', N'2')
    ;

    INSERT INTO [dbo].[MstProduct] ([CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'3', NULL, NULL, N'Pensil', N'Pensil 2b', N'3')
    ;

    SET IDENTITY_INSERT [dbo].[MstProduct] OFF
    ;

    -- ----------------------------
-- Records of MstUser
-- ----------------------------
    SET IDENTITY_INSERT [dbo].[MstUser] ON
    ;

    INSERT INTO [dbo].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [IDAkses], [IDUser], [ModifiedBy], [ModifiedDate], [NoHp], [username], [Nama], [OTP], [Password], [Email], [Alamat], [LinkProfilePicture], [ProfilePicture]) VALUES (N'1', N'2002-12-12', N'1', N'2025-03-07 21:41:59.211000', N'1', N'1', NULL, NULL, N'081286111111', N'admin.123', N'Admin Username', N'$2a$11$u.uyJ4mOI1xsol0YdqzXf.ncaS0GMncqXq3yBS6prjgtMlRr2Lyj6', N'$2a$11$uppIarADlxgvOonkvZdBLuciv9HLL12/QiNOK/zE2BWfkngm9ELK.', N'admin.123@gmail.com', N'Tangsel Tangsel Tangsel Tangsel Tangsel', NULL, NULL)
    ;

    INSERT INTO [dbo].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [IDAkses], [IDUser], [ModifiedBy], [ModifiedDate], [NoHp], [username], [Nama], [OTP], [Password], [Email], [Alamat], [LinkProfilePicture], [ProfilePicture]) VALUES (N'1', N'2002-12-12', N'1', N'2025-03-07 21:41:59.211000', N'2', N'2', NULL, NULL, N'081286111112', N'member.123', N'Member Username', N'$2a$11$u.uyJ4mOI1xsol0YdqzXf.ncaS0GMncqXq3yBS6prjgtMlRr2Lyj6', N'$2a$11$aySe9JOkUlGuu9Q5aK7No.uKkUqWuFEYTfPD6BzfpHDB7eOS5Gfza', N'member.123@gmail.com', N'Jakarta Tangsel Tangsel Tangsel Tangsel', NULL, NULL)
    ;

    SET IDENTITY_INSERT [dbo].[MstUser] OFF
    ;