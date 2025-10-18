# LLM API Key Setup Guide

This repository uses AI-powered code review through the Presubmit.ai workflow. To enable this feature, you need to configure the `LLM_API_KEY` secret.

## Quick Setup

### Step 1: Get Your Gemini API Key

1. Visit [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Sign in with your Google account
3. Click **"Create API Key"**
4. Copy the generated API key

### Step 2: Configure the Secret in GitHub

1. Go to your repository: `https://github.com/[YOUR_USERNAME]/java-design-patterns`
2. Click **Settings** → **Secrets and variables** → **Actions**
3. Click **"New repository secret"**
4. Set:
   - **Name**: `LLM_API_KEY`
   - **Secret**: Your Gemini API key from Step 1
5. Click **"Add secret"**

### Step 3: Verify Setup

After adding the secret, the next PR or workflow run will automatically use AI review.

## Model Configuration

The workflow is configured to use `gemini-1.5-flash` model. If you need to change this:

1. Edit `.github/workflows/presubmit.yml`
2. Update the `LLM_MODEL` environment variable
3. Available models include:
   - `gemini-1.5-pro`
   - `gemini-1.5-flash` (default)
   - `gemini-pro`

## Troubleshooting

### Common Issues

**Error: "LLM_API_KEY secret is not configured"**
- Solution: Follow Step 2 above to add the secret

**Error: "Model not found" or 404 errors**
- Check if your API key has access to the specified model
- Try using `gemini-1.5-pro` instead of `gemini-1.5-flash`
- Verify your API key is valid and active

**Workflow skips AI review**
- This is normal behavior when `LLM_API_KEY` is not configured
- The workflow will continue without AI review
- Add the secret to enable AI review

### Getting Help

If you encounter issues:
1. Check the GitHub Actions logs for detailed error messages
2. Verify your API key is correct and has proper permissions
3. Ensure you're using a supported model name

## Benefits of AI Review

When properly configured, the AI reviewer will:
- ✅ Analyze code quality and suggest improvements
- ✅ Check for potential bugs and security issues
- ✅ Provide feedback on code style and best practices
- ✅ Help maintain consistent coding standards

The AI review is **optional** - the main CI/CD pipeline will work without it.