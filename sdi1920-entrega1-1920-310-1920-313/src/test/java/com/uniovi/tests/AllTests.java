package com.uniovi.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddPublicationAndImageTest.class, AddPublicationTest.class, AdminUserListTest.class,
		FriendListTest.class, InternationalizationTest.class, ListAndAcceptFriendRequestTest.class,
		ListFriendPublicationTest.class, ListPublicationTest.class, LogInTest.class, LogoutTest.class,
		MultipleUserDeleteTest.class, SearchUsersTest.class, SecurityTest.class, SendFriendRequestTest.class,
		SignUpTest.class, UserListTest.class })
public class AllTests {

}
