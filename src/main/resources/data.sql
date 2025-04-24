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

    INSERT INTO [dbo].[MstProduct] ([IsDeleted], [CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'1', N'2025-03-10 18:32:05.000000', N'1', NULL, NULL, N'Kursi', N'Kursi warna abu-abu', N'1')
    ;

    INSERT INTO [dbo].[MstProduct] ([IsDeleted], [CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'1', N'2025-03-10 18:32:05.000000', N'2', NULL, NULL, N'Laptop', N'Laptop merek ACER', N'2')
    ;

    INSERT INTO [dbo].[MstProduct] ([IsDeleted], [CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'1', N'2025-03-10 18:32:05.000000', N'3', NULL, NULL, N'Pensil', N'Pensil 2b', N'3')
    ;

    INSERT INTO [dbo].[MstProduct] ([CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'4', NULL, NULL, N'Kursi', N'Kursi warna biru', N'1')
    ;

    INSERT INTO [dbo].[MstProduct] ([CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'5', NULL, NULL, N'Laptop', N'Laptop merek Lenovo', N'2')
    ;

    INSERT INTO [dbo].[MstProduct] ([CreatedBy], [CreatedDate], [IDProduct], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi], [IDProductCategory]) VALUES (N'1', N'2025-03-10 18:32:05.000000', N'6', NULL, NULL, N'Pensil', N'Pensil 2b', N'3')
    ;

    SET IDENTITY_INSERT [dbo].[MstProduct] OFF
    ;

-- ----------------------------
-- Records of MstUser
-- ----------------------------
SET IDENTITY_INSERT [dbo].[MstUser] ON
    ;

    INSERT INTO [dbo].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [IDAkses], [IDUser], [ModifiedBy], [ModifiedDate], [NoHp], [username], [Nama], [OTP], [Password], [Email], [Alamat], [LinkProfilePicture], [ProfilePicture]) VALUES (N'1', N'2002-12-12', N'1', N'2025-03-07 21:41:59.211000', N'1', N'1', NULL, NULL, N'081286111111', N'admin.123', N'Admin Username', N'$2a$11$u.uyJ4mOI1xsol0YdqzXf.ncaS0GMncqXq3yBS6prjgtMlRr2Lyj6', N'$2a$11$yyG9InToxNi1l/bovwz6PuCPyzE.bV/l.v/usJ7VinOGhhVxQy7J6', N'admin.123@gmail.com', N'Tangsel Tangsel Tangsel Tangsel Tangsel', NULL, NULL)
    ;

    INSERT INTO [dbo].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [IDAkses], [IDUser], [ModifiedBy], [ModifiedDate], [NoHp], [username], [Nama], [OTP], [Password], [Email], [Alamat], [LinkProfilePicture], [ProfilePicture]) VALUES (N'1', N'2002-12-12', N'1', N'2025-03-07 21:41:59.211000', N'2', N'2', NULL, NULL, N'081286111112', N'member.123', N'Member Username', N'$2a$11$u.uyJ4mOI1xsol0YdqzXf.ncaS0GMncqXq3yBS6prjgtMlRr2Lyj6', N'$2a$11$SV5suDoAAn7C9lbfTq581.ZE173Lg8bQHwGXMSs1SWQP8gr11jleu', N'member.123@gmail.com', N'Jakarta Tangsel Tangsel Tangsel Tangsel', NULL, NULL)
    ;

    INSERT INTO [dbo].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [IDAkses], [IDUser], [ModifiedBy], [ModifiedDate], [NoHp], [username], [Nama], [OTP], [Password], [Email], [Alamat], [LinkProfilePicture], [ProfilePicture]) VALUES (N'1', N'2002-12-12', N'1', N'2025-03-07 21:41:59.211000', N'2', N'3', NULL, NULL, N'081286111112', N'divisi.123', N'divisi Username', N'$2a$11$GH5G53MRlobxSs2GLLRgk.vOX9QfczcLaF.n29Xt7mpUcsPkJpxFS', N'$2a$11$772P1az53IxsIgepElhYh.1waGpm6.XXj0FXt7dSWyh7o6I4l65ka', N'divisi.123@gmail.com', N'Jakarta Tangsel Tangsel Tangsel Tangsel', NULL, NULL)
    ;

    SET IDENTITY_INSERT [dbo].[MstUser] OFF
    ;

-- ----------------------------
-- Records of MstStatus
-- ----------------------------
SET IDENTITY_INSERT [dbo].[MstStatus] ON
    ;

    INSERT INTO [dbo].[MstStatus] ([IDStatus], [Nama], [ModifiedBy], [ModifiedDate], [CreatedBy], [CreatedDate]) VALUES (N'1', N'Waiting for Approval', NULL, NULL, N'1', N'2025-03-10 18:33:19.000000')
    ;

    INSERT INTO [dbo].[MstStatus] ([IDStatus], [Nama], [ModifiedBy], [ModifiedDate], [CreatedBy], [CreatedDate]) VALUES (N'2', N'Approved', NULL, NULL, N'1', N'2025-03-10 18:33:19.000000')
    ;

    SET IDENTITY_INSERT [dbo].[MstStatus] OFF
    ;

-- ----------------------------
-- Records of MstTransaction
-- ----------------------------
SET IDENTITY_INSERT [dbo].[MstTransaction] ON
    ;

    INSERT INTO [dbo].[MstTransaction] ([IDStatus], [IDTransaction], [IDDivision], [IDAdmin], [ModifiedBy], [ModifiedDate], [CreatedBy], [CreatedDate]) VALUES (N'2', N'1', N'2', N'1', N'1', N'2025-03-10 18:33:19.000000', N'2', N'2025-03-10 18:33:19.000000')
    ;

    INSERT INTO [dbo].[MstTransaction] ([IDStatus], [IDTransaction], [IDDivision], [IDAdmin], [ModifiedBy], [ModifiedDate], [CreatedBy], [CreatedDate]) VALUES (N'1', N'2', N'2', NULL, NULL, NULL, N'2', N'2025-03-10 18:33:19.000000')
    ;

    INSERT INTO [dbo].[MstTransaction] ([IDStatus], [IDTransaction], [IDDivision], [IDAdmin], [ModifiedBy], [ModifiedDate], [CreatedBy], [CreatedDate]) VALUES (N'1', N'3', N'3', NULL, NULL, NULL, N'3', N'2025-03-10 18:33:19.000000')
    ;

    SET IDENTITY_INSERT [dbo].[MstTransaction] OFF
    ;

---- ----------------------------
---- Records of TransactionDetail
---- ----------------------------
    INSERT INTO [dbo].[TransactionDetail] ([IDTransaction], [IDProduct]) VALUES (N'1', N'1')
    ;

    INSERT INTO [dbo].[TransactionDetail] ([IDTransaction], [IDProduct]) VALUES (N'1', N'2')
    ;

    INSERT INTO [dbo].[TransactionDetail] ([IDTransaction], [IDProduct]) VALUES (N'1', N'3')
    ;

    INSERT INTO [dbo].[TransactionDetail] ([IDTransaction], [IDProduct]) VALUES (N'2', N'4')
    ;

    INSERT INTO [dbo].[TransactionDetail] ([IDTransaction], [IDProduct]) VALUES (N'3', N'5')
    ;

