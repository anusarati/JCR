/* Given with SQLite Blessing
 *
 * The author disclaims copyright to this source code. In place of a legal notice, here is a blessing:
 *
 *     May you do good and not evil.
 *
 *         May you find forgiveness for yourself and forgive others.
 *
 *             May you share freely, never taking more than you give.
 *
 */

/* thanks to Mr. Kumar for teaching me, Jakob Jenkov for his Java tutorials,
 * Steve Jansen for his Batch scripting tutorial, Microsoft for their documentation, 
 * Steve Parker for his shell scripting tutorial, OpenJDK and AdoptOpenJDK for the JDK, FSF/GNU for the GNU Project,
 * the Linux project, The Linux Documentation Project, Linux Lite, Oracle and open source community for Graal,
 * The Coding Train, Derek Banas, The Cherno, freeCodeCamp, cppreference.com, cplusplus.com, William Lin, errichto,
 * Mendel Cooper for the Advanced Bash-Scripting Guide, etc.
 */
public class jcr
{
	public static ProcessBuilder pb = new ProcessBuilder().inheritIO();
	public static String[] args;
	public static class p // split arguments, somewhat like Bash expansions
	{
		// split arguments into String[] with recursion
		// idea is to minimize local stack size of functions
		public static int n, i, j, k, l, al, cl;
		public static boolean split, backslash;
		public static char fq;
		public static String[] args, command;
		public static String arg;
		public static char[] comarg = new char[1];
		public static String[] command(String c, String[] a)
		{
			args = a;
			n = a.length;
			i = j = l = fq = 0;
			k = 1;
			split = backslash = false;
			arg = args[0];
			al = arg.length();
			decide();
			return command(c);
		}
		public static String[] command(String c)
		{
			command[0] = c;
			return command;
		}
		public static void comarg()
		{
			if (comarg.length < l) comarg = new char[l];
			cl = l;
		}
		public static void arg()
		{
			arg = args[i];
			al = arg.length();
		}
		public static void proceed()
		{
			if (++j < al)
			{
				decide();
			}
			else if (++i < n)
			{
				arg();
				j = 0;
				int p = l;
				k++; l = 0;
				decide();
				l = p;
				comarg();
				arg();
				j = al-1;
			}
			else
			{
				command = new String[k+1];
				comarg();
				i--; j--;
			}
		}
		public static void pass()
		{
			int p = l;
			k++; l = 0;
			if (++j < al) decide();
			else if (++i < n)
			{
				arg();
				j = 0;
				decide();
				arg();
				j = al-1;
			}
			else
			{
				command = new String[k--];
				i--; j--;
			}
			l = p;
			comarg();
		}
		public static boolean qcase(char q)
		{
			if (backslash)
			{
				defaultcase();
				return true;
			}
			else if (fq == q)
			{
				fq = 0;
				pass();
			}
			else if (fq == 0)
			{
				fq = q;
				proceed();
			}
			else
			{
				defaultcase();
				return true;
			}
			return false;
		}
		public static void defaultcase()
		{
			l++; backslash = false;
			proceed();
		}
		/*
		 * the Venerable Sāriputta, the Buddha's chief disciple, saw that adhimokkha,
		 * which I saw was translated as decision by the Venerable Ṭhanissāro,
		 * was one of the dhamma in the first, second, third, and fourth jhāna
		 */
		public static void decide()
		{
			//System.out.printf("%d,%d,%d,%d\n",i,j,k,l);
			char c = arg.charAt(j);
			boolean w = true;
			switch (c)
			{
				case ' ':
				{
					if (fq != 0)
					{
						l++;
						proceed();
					}
					else if (backslash)
					{
						l++;
						backslash = false;
						proceed();
					}
					else 
					{
						w = false;
						if (l != 0) pass();
						else proceed();
					}
					break;
				}
				case '\\':
				{
					if (backslash) defaultcase();
					else
					{
						backslash = true; w = false;
						proceed();
					}
					break;
				}
				case '\'':
				{
					w = qcase('\'');
					break;
				}
				case '"':
				{
					w = qcase('"');
					break;
				}
				default:
				{
					defaultcase();
				}
			}
			if (j-- == 0) i--;
			if (w)
			{
				comarg[--l] = c;
				if (l == 0) command[k--] = new String(comarg,0,cl);
			}
			//System.out.printf("%d,%d,%c,%s\n",k,l,c,w);
		}
	}
	public static int r(String[] command)
	{
		try
		{
			for (String s : command) System.out.println(s);
			System.out.println();
			return pb.command(command).start().waitFor();
		}
		catch (Throwable t)
		{
			return -1;
		}
	}
	public static int r(String c, String[] a)
	{
		String[] command = p.command(c,a);
		return r(command);
	}
	public static int r(String c)
	{
		return r(p.command(c));
	}
	public static void cr(String[] a, String[] b)
	{
		int e = r("javac",a);
		if (e == 0) System.exit(r("java",b));// run if compilation exited with 0, like Bash &&
		else System.exit(e);
	}
	public static void cr(String[] a)
	{
		int e = r("javac",a);
		if (e == 0) System.exit(r("java"));
		else System.exit(e);
	}
	public static void withExtension(String[] args, int i)
	{
		if (!args[i].endsWith(".java")) args[i] += ".java";
	}
	public static String[] a(int... indices)
	{
		String[] a = new String[indices.length];
		for (int i = 0; i < indices.length; i++) a[i] = args[indices[i]];
		return a;
	}
	public static void main(String[] args)
	{
		jcr.args = args;
		switch (args.length)
		{
			case 1:
			{
				withExtension(args,0);
				String nameString = args[0]; // I read a translation where the Buddha said that name oppresses everything
				String[] a = new String[]{nameString};
				cr(a);
				break;
			}
			case 2:
			{
				withExtension(args,0);
				cr(a(0),a(0,1));
				break;
			}
			case 3:
			{
				withExtension(args,1);
				cr(a(0,1),a(1,2));
				break;
			}
			case 4:
			{
				withExtension(args,2);	
				cr(a(0,2),a(1,2,3));
				break;
			}
			default:
			{
				System.err.println("jcr name");
				System.err.println("jcr name \"main args\"");
				System.err.println("jcr \"javac args\" name \"main args\"");
				System.err.println("jcr \"javac args\" \"java args (except name)\" name \"main args\"");
				System.exit(1);
			}
		}
	}
}
