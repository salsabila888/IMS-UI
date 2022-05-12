
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapTest {

	public static void main(String[] args) {
		LdapContext ldapContext = getLdapContext();
		System.out.println("Search control");
		SearchControls searchControls = getSearchControls();
		SearchControls searchControlsRole = getSearchRole();
		getUserInfo("24027", ldapContext, searchControls, searchControlsRole);
	}

	private static LdapContext getLdapContext() {
		LdapContext ctx = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "Simple");
			env.put(Context.SECURITY_PRINCIPAL, "cn=devldap3,cn=Users,o=bni,dc=co,dc=id");// input user & password for
																							// access to ldap
			env.put(Context.SECURITY_CREDENTIALS, "devldap1234");
			env.put(Context.PROVIDER_URL, "LDAP://192.168.12.134:389");
			env.put(Context.REFERRAL, "follow");
			ctx = new InitialLdapContext(env, null);
			System.out.println("LDAP Connection: COMPLETE");
		} catch (NamingException nex) {
			System.out.println("LDAP Connection: FAILED");
			nex.printStackTrace();
		}
		return ctx;
	}

	private static SearchControls getSearchControls() {
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String[] attrIDs = { "uid", "cn", "mail", "branchalias" };
		cons.setReturningAttributes(attrIDs);
		return cons;
	}

	private static SearchControls getSearchRole() {
		SearchControls consRole = new SearchControls();
		consRole.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String[] attrIDs = { "captionrole" };
		consRole.setReturningAttributes(attrIDs);
		return consRole;
	}

	private static void getUserInfo(String userName, LdapContext ctx, SearchControls searchControls,
			SearchControls searchControlsRole) {
		System.out.println("*** " + userName + " ***");
		try {
			NamingEnumeration<SearchResult> answer = ctx.search("ou=accounts,o=bni,dc=co,dc=id", "uid=" + userName,
					searchControls);

			if (answer.hasMore()) {
				Attributes attrs = answer.next().getAttributes();
				String attribute = "";
				attribute = attrs.toString();
				System.out.println("attribute name : Userid, value : " + attrs.get("uid").get().toString());
				// System.out.println("attribute name : username, value : " +
				// attrs.get("sAMAccountName").get().toString());
				System.out.println("attribute name : Name, value : " + attrs.get("cn").get().toString());
				System.out.println("attribute name : Email, value : " + attrs.get("mail").get().toString());

				answer = ctx.search("ou=bniapps,o=bni,dc=co,dc=id", "uid=" + userName, searchControlsRole);
				if (answer.hasMore()) {
					attrs = answer.next().getAttributes();
					System.out.println("attribute name : Role, value : " + attrs.get("captionrole").get().toString());

					System.out.println(attribute + attrs.toString());
				}
			} else {
				System.out.println("user not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
