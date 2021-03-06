package uk.gov.digital.ho.egar.aircraft.test.utils.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * 
 * @author CIVICA
 * @see http://www.vogella.com/tutorials/Hamcrest/article.html
 *
 */
public class RegexMatcher extends TypeSafeMatcher<String> {

        private final String regex;

        public RegexMatcher(final String regex) {
                this.regex = regex;
        }

        @Override
        public void describeTo(final Description description) {
                description.appendText("matches regular expression=`" + regex + "`");
        }

        @Override
        public boolean matchesSafely(final String string) {
                return string.matches(regex);
        }


         // matcher method you can call on this matcher class
    public static RegexMatcher matchesRegex(final String regex) {
        return new RegexMatcher(regex);
    }
}
