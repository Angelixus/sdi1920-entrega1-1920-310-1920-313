package com.uniovi.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LogInTest.class, SignUpTest.class, LogoutTest.class, UserListTest.class, SearchUsersTest.class,
		SendFriendRequestTest.class, ListAndAcceptFriendRequestTest.class, FriendListTest.class })
public class AllTests {

}
