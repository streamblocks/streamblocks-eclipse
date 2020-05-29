define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "List|String|_|action|actor|all|and|as|begin|bool|case|div|do|double|else|elsif|end|endinvariant|endlambda|endlet|endproc|endschedule|entities|entity|external|float|for|foreach|fsm|function|guard|if|import|in|initialize|int|invariant|lambda|let|map|mod|namespace|network|not|of|old|or|package|priority|proc|procedure|regexp|repeat|schedule|set|structure|then|type|uint|unit|var|while";
		this.$rules = {
			"start": [
				{token: "lparen", regex: "[\\[({]"},
				{token: "rparen", regex: "[\\])}]"},
				{token: "keyword", regex: "\\b(?:" + keywords + ")\\b"}
			]
		};
	};
	oop.inherits(HighlightRules, mTextHighlightRules.TextHighlightRules);
	
	var Mode = function() {
		this.HighlightRules = HighlightRules;
	};
	oop.inherits(Mode, mText.Mode);
	Mode.prototype.$id = "xtext/cal";
	Mode.prototype.getCompletions = function(state, session, pos, prefix) {
		return [];
	}
	
	return {
		Mode: Mode
	};
});
