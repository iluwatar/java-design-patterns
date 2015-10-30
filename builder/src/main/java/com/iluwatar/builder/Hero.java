package com.iluwatar.builder;

/**
 * 
 * Hero, the class with many parameters.
 * 
 */
public class Hero {

	private final Profession profession;
	private final String name;
	private final HairType hairType;
	private final HairColor hairColor;
	private final Armor armor;
	private final Weapon weapon;

	public Profession getProfession() {
		return profession;
	}

	public String getName() {
		return name;
	}

	public HairType getHairType() {
		return hairType;
	}

	public HairColor getHairColor() {
		return hairColor;
	}

	public Armor getArmor() {
		return armor;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("This is a ");
		sb.append(profession);
		sb.append(" named ");
		sb.append(name);
		if (hairColor != null || hairType != null) {
			sb.append(" with ");
			if (hairColor != null) {
				sb.append(hairColor);
				sb.append(" ");
			}
			if (hairType != null) {
				sb.append(hairType);
				sb.append(" ");
			}
			sb.append(hairType != HairType.BALD ? "hair" : "head");
		}
		if (armor != null) {
			sb.append(" wearing ");
			sb.append(armor);
		}
		if (weapon != null) {
			sb.append(" and wielding a ");
			sb.append(weapon);
		}
		sb.append(".");
		return sb.toString();
	}

	private Hero(HeroBuilder builder) {
		this.profession = builder.profession;
		this.name = builder.name;
		this.hairColor = builder.hairColor;
		this.hairType = builder.hairType;
		this.weapon = builder.weapon;
		this.armor = builder.armor;
	}

	/**
	 * 
	 * The builder class.
	 * 
	 */
	public static class HeroBuilder {

		private final Profession profession;
		private final String name;
		private HairType hairType;
		private HairColor hairColor;
		private Armor armor;
		private Weapon weapon;

		public HeroBuilder(Profession profession, String name) {
			if (profession == null || name == null) {
				throw new IllegalArgumentException(
						"profession and name can not be null");
			}
			this.profession = profession;
			this.name = name;
		}

		public HeroBuilder withHairType(HairType hairType) {
			this.hairType = hairType;
			return this;
		}

		public HeroBuilder withHairColor(HairColor hairColor) {
			this.hairColor = hairColor;
			return this;
		}

		public HeroBuilder withArmor(Armor armor) {
			this.armor = armor;
			return this;
		}

		public HeroBuilder withWeapon(Weapon weapon) {
			this.weapon = weapon;
			return this;
		}

		public Hero build() {
			return new Hero(this);
		}
	}
}
