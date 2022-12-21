package com.epam.entity;

public class UserDto {

	private int id;

	private String name;

	private String address;

	private int age;

	private String username;

	private String password;

	public UserDto() {
	}

	public UserDto(String name, String address, int age, String username, String password) {
		super();
		this.name = name;
		this.address = address;
		this.username = username;
		this.password = password;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
