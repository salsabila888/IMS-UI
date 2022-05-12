import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import java.util.Date;
import java.util.Properties;

public class LdapLoginTest {

    public static void main(String[] args) {
        System.out.println("run: " + new Date());
        try {
			String ldapurl = "LDAP://192.168.12.134:389";
			String ldapuser = "cn=devldap3,cn=Users,o=bni,dc=co,dc=id";
			String ldappassword = "devldap1234";
			
			String userid = "24027";
			String password = "bni1234";
			
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			props.put(Context.PROVIDER_URL, ldapurl);
			props.put(Context.SECURITY_PRINCIPAL, ldapuser);// adminuser - User with special priviledge, dn user
			props.put(Context.SECURITY_CREDENTIALS, ldappassword);// dn user password
			
			InitialDirContext context = new InitialDirContext(props);

			SearchControls ctrls = new SearchControls();
			ctrls.setReturningAttributes(new String[] { "uid", "cn", "mail" });
			ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			NamingEnumeration<javax.naming.directory.SearchResult> answers = context.search("ou=accounts,o=bni,dc=co,dc=id", "uid=" + userid, ctrls);
			javax.naming.directory.SearchResult result = answers.nextElement();
			String user = result.getNameInNamespace();
			
			try {
				props = new Properties();
				props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				props.put(Context.PROVIDER_URL, ldapurl);
				props.put(Context.SECURITY_PRINCIPAL, user);
				props.put(Context.SECURITY_CREDENTIALS, password);

				context = new InitialDirContext(props);
				System.out.println(" Auth is accepted : " + context.getNameInNamespace());
				
			} catch (AuthenticationException e) {
				System.out.println(" Auth is False");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}